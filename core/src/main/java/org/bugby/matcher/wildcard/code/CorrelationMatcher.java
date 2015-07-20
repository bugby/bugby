package org.bugby.matcher.wildcard.code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.lang.model.element.Element;

import org.bugby.api.BugbyException;
import org.bugby.api.MatchingContext;
import org.bugby.api.MatchingValueKey;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherExecutionType;
import org.bugby.api.annotation.Correlation;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.Tree;

public class CorrelationMatcher extends DefaultTreeMatcher {
	private final MatchingValueKey matchingKey;
	private final String key;
	private Comparator<Tree> comparator;
	private final TreeMatcher annotatedNodeMatcher;
	private final Correlation annotation;
	private final Element element;

	public CorrelationMatcher(AnnotationTree annotationNode, Tree annotatedNode, TreeMatcher annotatedNodeMatcher) {
		super(annotatedNode);
		element = TreeUtils.getElementFromDeclaration(annotatedNode);
		annotation = element.getAnnotation(Correlation.class);
		if (annotation == null) {
			throw new RuntimeException("The node is not annotated with OrSet annotation:" + annotatedNode);
		}
		key = annotation.key();
		this.annotatedNodeMatcher = annotatedNodeMatcher;
		matchingKey = new MatchingValueKey("CORRELATION", "KEY:" + key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TreeMatcherExecutionType startMatching(boolean ordered, MatchingContext context) {
		if (comparator == null) {
			Class<?> comparatorClass = context.getClassAnnotationField(element, Correlation.class, "comparator");
			try {
				comparator = (Comparator<Tree>) comparatorClass.newInstance();
			}
			catch (InstantiationException | IllegalAccessException e) {
				throw new BugbyException("Unable to instantiate correlator comparator:" + e.getMessage(), e);
			}
		}
		return super.startMatching(ordered, context);
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		if (!annotatedNodeMatcher.matches(node, context)) {
			return false;
		}
		List<MatchInfo> correlatedNodes = context.getValue(matchingKey);
		if (correlatedNodes == null) {
			correlatedNodes = new ArrayList<>();
			context.putValue(matchingKey, correlatedNodes);
		}
		removeExistingMatch(correlatedNodes);
		if (correlatedNodes.isEmpty()) {
			correlatedNodes.add(new MatchInfo(this, node));
			return true;
		}
		boolean ok = comparator.compare(correlatedNodes.get(0).getNode(), node) == 0;
		if (ok) {
			correlatedNodes.add(new MatchInfo(this, node));
		}
		return ok;
	}

	protected void removeExistingMatch(List<MatchInfo> correlatedNodes) {
		for (Iterator<MatchInfo> iterator = correlatedNodes.iterator(); iterator.hasNext();) {
			if (iterator.next().getMatcher() == this) {
				iterator.remove();
				return;
			}
		}
	}

	private static class MatchInfo {
		private final TreeMatcher matcher;
		private final Tree node;

		public MatchInfo(TreeMatcher matcher, Tree node) {
			this.matcher = matcher;
			this.node = node;
		}

		public TreeMatcher getMatcher() {
			return matcher;
		}

		public Tree getNode() {
			return node;
		}

	}

}

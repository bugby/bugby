package org.bugby.matcher.wildcard.code;

import java.util.Collections;
import java.util.List;

import javax.lang.model.element.Element;

import org.bugby.api.MatchingContext;
import org.bugby.api.MatchingPath;
import org.bugby.api.MatchingValueKey;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherExecutionType;
import org.bugby.api.annotation.MatchCount;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.Tree;

public class MatchCountMatcher extends DefaultTreeMatcher {
	private final MatchingValueKey matchingKey;
	private final int min;
	private final int max;
	private final TreeMatcher annotatedNodeMatcher;

	public MatchCountMatcher(AnnotationTree annotationNode, Tree annotatedNode, TreeMatcher annotatedNodeMatcher) {
		super(annotatedNode);
		Element element = TreeUtils.getElementFromDeclaration(annotatedNode);
		MatchCount ann = element.getAnnotation(MatchCount.class);
		if (ann == null) {
			throw new RuntimeException("The node is not annotated with MatchCount annotation:" + annotatedNode);
		}
		min = ann.min();
		max = ann.max();
		this.annotatedNodeMatcher = annotatedNodeMatcher;
		matchingKey = new MatchingValueKey("METHOD", "COUNT:" + getId());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		boolean matched = annotatedNodeMatcher.matches(node, context);

		if (matched) {
			context.putValue(matchingKey, (Integer) context.getValue(matchingKey) + 1);
		}
		return matched;
	}

	@Override
	public TreeMatcherExecutionType startMatching(boolean ordered, MatchingContext context) {
		context.putValue(matchingKey, 0);
		return super.startMatching(ordered, context);
	}

	@Override
	public List<List<MatchingPath>> endMatching(List<List<MatchingPath>> currentResult, MatchingContext context) {
		int count = (Integer) context.getValue(matchingKey);
		if (count >= min && count <= max) {
			return super.endMatching(currentResult, context);
		}
		// return empty result as there is no match
		return Collections.emptyList();
	}

}

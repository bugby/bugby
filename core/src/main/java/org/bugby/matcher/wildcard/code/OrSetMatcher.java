package org.bugby.matcher.wildcard.code;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;

import org.bugby.api.MatchingContext;
import org.bugby.api.MatchingPath;
import org.bugby.api.MatchingValueKey;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherExecutionType;
import org.bugby.api.annotation.OrSet;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.Tree;

public class OrSetMatcher extends DefaultTreeMatcher {
	private final MatchingValueKey matchingKey;
	private final int set;
	private final TreeMatcher annotatedNodeMatcher;

	public OrSetMatcher(AnnotationTree annotationNode, Tree annotatedNode, TreeMatcher annotatedNodeMatcher) {
		super(annotatedNode);
		Element element = TreeUtils.getElementFromDeclaration(annotatedNode);
		OrSet ann = element.getAnnotation(OrSet.class);
		if (ann == null) {
			throw new RuntimeException("The node is not annotated with OrSet annotation:" + annotatedNode);
		}
		set = ann.value();
		this.annotatedNodeMatcher = annotatedNodeMatcher;
		matchingKey = new MatchingValueKey("ORSET", "SET:" + set);
	}

	@Override
	public TreeMatcherExecutionType startMatching(boolean ordered, MatchingContext context) {
		List<TreeMatcher> setMatchers = context.getValue(matchingKey);
		if (setMatchers == null) {
			setMatchers = new ArrayList<TreeMatcher>();
		}
		setMatchers.add(annotatedNodeMatcher);
		context.putValue(matchingKey, setMatchers);
		//keep only the first one in the list
		return setMatchers.size() == 1 ? TreeMatcherExecutionType.keep : TreeMatcherExecutionType.ignore;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		List<TreeMatcher> setMatchers = context.getValue(matchingKey);
		//at least one of the matchers should match
		for (TreeMatcher matcher : setMatchers) {
			if (matcher.matches(node, context)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<List<MatchingPath>> endMatching(List<List<MatchingPath>> currentResult, MatchingContext context) {
		List<TreeMatcher> setMatchers = context.getValue(matchingKey);
		if (setMatchers != null) {
			setMatchers.remove(annotatedNodeMatcher);
		}
		return super.endMatching(currentResult, context);
	}

}

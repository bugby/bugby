package org.bugby.engine.matcher.expression;

import java.util.List;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;

public class MethodInvocationMatcher extends DefaultMatcher implements TreeMatcher {
	private final MethodInvocationTree patternNode;
	private final TreeMatcher methodSelectMatcher;
	private final List<TreeMatcher> typeArgumentsMathers;
	private final List<TreeMatcher> argumentsMatchers;

	public MethodInvocationMatcher(MethodInvocationTree patternNode, TreeMatcher methodSelectMatcher, List<TreeMatcher> typeArgumentsMathers,
			List<TreeMatcher> argumentsMatchers) {
		this.patternNode = patternNode;
		this.methodSelectMatcher = methodSelectMatcher;
		this.typeArgumentsMathers = typeArgumentsMathers;
		this.argumentsMatchers = argumentsMatchers;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof MethodInvocationTree)) {
			return HashMultimap.create();
		}
		MethodInvocationTree mt = (MethodInvocationTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, mt.getMethodSelect(), methodSelectMatcher, context);
		result = matchOrderedChildren(result, node, mt.getTypeArguments(), typeArgumentsMathers, context);
		result = matchOrderedChildren(result, node, mt.getArguments(), argumentsMatchers, context);

		return result;
	}
}

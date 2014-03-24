package org.bugby.engine.matcher.expression;

import java.util.List;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;

public class MethodInvocationMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final MethodInvocationTree patternNode;
	private final TreeMatcher methodSelectMatcher;
	private final List<TreeMatcher> typeArgumentsMathers;
	private final List<TreeMatcher> argumentsMatchers;

	public MethodInvocationMatcher(MethodInvocationTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.methodSelectMatcher = factory.build(patternNode.getMethodSelect());
		this.typeArgumentsMathers = build(factory, patternNode.getTypeArguments());
		this.argumentsMatchers = build(factory, patternNode.getArguments());
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

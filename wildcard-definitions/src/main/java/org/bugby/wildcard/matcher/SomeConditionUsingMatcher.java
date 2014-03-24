package org.bugby.wildcard.matcher;

import java.util.List;

import org.bugby.api.javac.TreeUtils;
import org.bugby.api.javac.TypesUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;

public class SomeConditionUsingMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final MethodInvocationTree patternNode;
	private final List<TreeMatcher> argumentsMatchers;

	public SomeConditionUsingMatcher(MethodInvocationTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.argumentsMatchers = build(factory, patternNode.getArguments());
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof ExpressionTree)) {
			return HashMultimap.create();
		}
		ExpressionTree mt = (ExpressionTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchSelf(result, node, TypesUtils.isBooleanType(TreeUtils.elementFromUse(mt).asType()), context);
		result = matchUnorderedChildren(result, mt, TreeUtils.descendantsOfType(mt, Tree.class), argumentsMatchers, context);

		return result;
	}

}

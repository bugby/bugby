package org.bugby.wildcard.matcher;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;

public class SomeValueMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final MethodInvocationTree patternNode;

	public SomeValueMatcher(MethodInvocationTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof ExpressionTree)) {
			return HashMultimap.create();
		}
		Multimap<TreeMatcher, Tree> result = null;
		result = matchSelf(result, node, true, context);

		return result;
	}
}

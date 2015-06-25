package org.bugby.wildcard.matcher;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;

public class SomeExpressionThrowingMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final MethodInvocationTree patternNode;

	public SomeExpressionThrowingMatcher(Tree patternNode, TreeMatcherFactory factory) {
		this.patternNode = removeExpressionStatement(patternNode);
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		//		if (!(node instanceof ExpressionTree)) {
		//			return match.done(false);
		//		}

		match.self(true);
		return match.done(true);
	}
}

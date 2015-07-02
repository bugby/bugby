package org.bugby.matcher.wildcard.expr;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.Tree;

public class SomeExpressionThrowingMatcher extends DefaultTreeMatcher implements TreeMatcher {
	public SomeExpressionThrowingMatcher(Tree patternNode, TreeMatcherFactory factory) {
		super(removeExpressionStatement(patternNode));
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

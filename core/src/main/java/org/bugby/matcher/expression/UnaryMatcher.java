package org.bugby.matcher.expression;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.Tree;
import com.sun.source.tree.UnaryTree;

public class UnaryMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher expressionMatcher;

	public UnaryMatcher(UnaryTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		this.expressionMatcher = factory.build(patternNode.getExpression());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof UnaryTree)) {
			return match.done(false);
		}
		UnaryTree mt = (UnaryTree) node;

		match.self(mt.getKind().equals(((UnaryTree) getPatternNode()).getKind()));
		match.child(mt.getExpression(), expressionMatcher);

		return match.done();
	}

}

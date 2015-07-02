package org.bugby.matcher.statement;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.Tree;

public class ReturnMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher expressionMatcher;

	public ReturnMatcher(ReturnTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);

		this.expressionMatcher = factory.build(patternNode.getExpression());
	}

	public TreeMatcher getExpressionMatcher() {
		return expressionMatcher;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof ReturnTree)) {
			return match.done(false);
		}
		ReturnTree mt = (ReturnTree) node;

		match.child(mt.getExpression(), expressionMatcher);

		return match.done();
	}

}

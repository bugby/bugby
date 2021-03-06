package org.bugby.matcher.statement;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.Tree;

public class ExpressionStatementMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher expressionMatcher;

	public ExpressionStatementMatcher(ExpressionStatementTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);

		this.expressionMatcher = factory.build(patternNode.getExpression());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		Tree toMatch = node;
		if (toMatch instanceof ExpressionStatementTree) {
			// matches if the child matches the expression as well
			toMatch = ((ExpressionStatementTree) toMatch).getExpression();
		}

		match.child(toMatch, expressionMatcher);

		return match.done();

	}

}

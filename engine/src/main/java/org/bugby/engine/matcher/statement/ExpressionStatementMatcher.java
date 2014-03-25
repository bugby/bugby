package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.Multimap;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.Tree;

public class ExpressionStatementMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final ExpressionStatementTree patternNode;
	private final TreeMatcher expressionMatcher;

	public ExpressionStatementMatcher(ExpressionStatementTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.expressionMatcher = factory.build(patternNode.getExpression());
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		Tree toMatch = node;
		if (toMatch instanceof ExpressionStatementTree) {
			// matches if the child matches the expression as well
			toMatch = ((ExpressionStatementTree) toMatch).getExpression();
		}

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, toMatch, expressionMatcher, context);

		return result;

	}

}

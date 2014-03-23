package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.Tree;

public class ExpressionStatementMatcher extends DefaultMatcher implements TreeMatcher {
	private final ExpressionStatementTree patternNode;
	private final TreeMatcher expressionMatcher;

	public ExpressionStatementMatcher(ExpressionStatementTree patternNode, TreeMatcher expressionMatcher) {
		this.patternNode = patternNode;
		this.expressionMatcher = expressionMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof ExpressionStatementTree)) {
			return HashMultimap.create();
		}
		ExpressionStatementTree ct = (ExpressionStatementTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, ct.getExpression(), expressionMatcher, context);

		return result;

	}

}

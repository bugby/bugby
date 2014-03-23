package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.EnhancedForLoopTree;
import com.sun.source.tree.Tree;

public class EnhancedForLoopMatcher extends DefaultMatcher implements TreeMatcher {
	private final EnhancedForLoopTree patternNode;
	private final TreeMatcher variableMatcher;
	private final TreeMatcher expressionMatcher;
	private final TreeMatcher statementMatcher;

	public EnhancedForLoopMatcher(EnhancedForLoopTree patternNode, TreeMatcher variableMatcher, TreeMatcher expressionMatcher,
			TreeMatcher statementMatcher) {
		this.patternNode = patternNode;
		this.variableMatcher = variableMatcher;
		this.expressionMatcher = expressionMatcher;
		this.statementMatcher = statementMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof EnhancedForLoopTree)) {
			return HashMultimap.create();
		}
		EnhancedForLoopTree ct = (EnhancedForLoopTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, ct.getVariable(), variableMatcher, context);
		result = matchChild(result, node, ct.getExpression(), expressionMatcher, context);
		result = matchChild(result, node, ct.getStatement(), statementMatcher, context);

		return result;
	}

}

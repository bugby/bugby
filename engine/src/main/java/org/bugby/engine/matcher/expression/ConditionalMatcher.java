package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ConditionalExpressionTree;
import com.sun.source.tree.Tree;

public class ConditionalMatcher extends DefaultMatcher implements TreeMatcher {
	private final ConditionalExpressionTree patternNode;
	private final TreeMatcher conditionMatcher;
	private final TreeMatcher thenMatcher;
	private final TreeMatcher elseMatcher;

	public ConditionalMatcher(ConditionalExpressionTree patternNode, TreeMatcher conditionMatcher, TreeMatcher thenMatcher,
			TreeMatcher elseMatcher) {
		this.patternNode = patternNode;
		this.conditionMatcher = conditionMatcher;
		this.thenMatcher = thenMatcher;
		this.elseMatcher = elseMatcher;
	}

	public ConditionalExpressionTree getPatternNode() {
		return patternNode;
	}

	public TreeMatcher getConditionMatcher() {
		return conditionMatcher;
	}

	public TreeMatcher getThenMatcher() {
		return thenMatcher;
	}

	public TreeMatcher getElseMatcher() {
		return elseMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof ConditionalExpressionTree)) {
			return HashMultimap.create();
		}
		ConditionalExpressionTree mt = (ConditionalExpressionTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, mt.getCondition(), conditionMatcher, context);
		result = matchChild(result, node, mt.getTrueExpression(), thenMatcher, context);
		result = matchChild(result, node, mt.getFalseExpression(), elseMatcher, context);

		return result;
	}

}

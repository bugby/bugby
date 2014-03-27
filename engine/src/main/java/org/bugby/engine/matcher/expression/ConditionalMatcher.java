package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.Multimap;
import com.sun.source.tree.ConditionalExpressionTree;
import com.sun.source.tree.Tree;

public class ConditionalMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final ConditionalExpressionTree patternNode;
	private final TreeMatcher conditionMatcher;
	private final TreeMatcher thenMatcher;
	private final TreeMatcher elseMatcher;

	public ConditionalMatcher(ConditionalExpressionTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.conditionMatcher = factory.build(patternNode.getCondition());
		this.thenMatcher = factory.build(patternNode.getTrueExpression());
		this.elseMatcher = factory.build(patternNode.getFalseExpression());
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
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof ConditionalExpressionTree)) {
			return match.done(false);
		}
		ConditionalExpressionTree mt = (ConditionalExpressionTree) node;

		
		match.child(mt.getCondition(), conditionMatcher);
		match.child(mt.getTrueExpression(), thenMatcher);
		match.child(mt.getFalseExpression(), elseMatcher);

		return match.done();
	}

}

package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.EnhancedForLoopTree;
import com.sun.source.tree.Tree;

public class EnhancedForLoopMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final EnhancedForLoopTree patternNode;
	private final TreeMatcher variableMatcher;
	private final TreeMatcher expressionMatcher;
	private final TreeMatcher statementMatcher;

	public EnhancedForLoopMatcher(EnhancedForLoopTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.variableMatcher = factory.build(patternNode.getVariable());
		this.expressionMatcher = factory.build(patternNode.getExpression());
		this.statementMatcher = factory.build(patternNode.getStatement());
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

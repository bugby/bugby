package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

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
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof EnhancedForLoopTree)) {
			return match.done(false);
		}
		EnhancedForLoopTree ct = (EnhancedForLoopTree) node;

		
		match.child(ct.getVariable(), variableMatcher);
		match.child(ct.getExpression(), expressionMatcher);
		match.child(ct.getStatement(), statementMatcher);

		return match.done();
	}

}

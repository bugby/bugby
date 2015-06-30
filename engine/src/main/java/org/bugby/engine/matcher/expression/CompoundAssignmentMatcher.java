package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.CompoundAssignmentTree;
import com.sun.source.tree.Tree;

public class CompoundAssignmentMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher variableMatcher;
	private final TreeMatcher expressionMatcher;

	public CompoundAssignmentMatcher(CompoundAssignmentTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		this.variableMatcher = factory.build(patternNode.getVariable());
		this.expressionMatcher = factory.build(patternNode.getExpression());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof CompoundAssignmentTree)) {
			return match.done(false);
		}
		CompoundAssignmentTree mt = (CompoundAssignmentTree) node;

		match.self(mt.getKind().equals(((CompoundAssignmentTree) getPatternNode()).getKind()));
		match.child(mt.getExpression(), expressionMatcher);
		match.child(mt.getVariable(), variableMatcher);

		return match.done();
	}
}

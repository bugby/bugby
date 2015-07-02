package org.bugby.matcher.expression;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

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

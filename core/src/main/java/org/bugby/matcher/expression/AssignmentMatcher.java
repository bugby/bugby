package org.bugby.matcher.expression;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.Tree;

public class AssignmentMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher variableMatcher;
	private final TreeMatcher expressionMatcher;

	public AssignmentMatcher(AssignmentTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		this.variableMatcher = factory.build(patternNode.getVariable());
		this.expressionMatcher = factory.build(patternNode.getExpression());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof AssignmentTree)) {
			return match.done(false);
		}
		AssignmentTree mt = (AssignmentTree) node;

		match.child(mt.getExpression(), expressionMatcher);
		match.child(mt.getVariable(), variableMatcher);

		return match.done();
	}

}

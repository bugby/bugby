package org.bugby.matcher.expression;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.InstanceOfTree;
import com.sun.source.tree.Tree;

public class InstanceofMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher typeMatcher;
	private final TreeMatcher expressionMatcher;

	public InstanceofMatcher(InstanceOfTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		this.typeMatcher = factory.build(patternNode.getType());
		this.expressionMatcher = factory.build(patternNode.getExpression());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof InstanceOfTree)) {
			return match.done(false);
		}
		InstanceOfTree mt = (InstanceOfTree) node;

		match.child(mt.getType(), typeMatcher);
		match.child(mt.getExpression(), expressionMatcher);

		return match.done();
	}

}

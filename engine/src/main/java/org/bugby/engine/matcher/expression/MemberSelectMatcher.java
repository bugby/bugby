package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.Tree;

public class MemberSelectMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher expressionMatcher;

	public MemberSelectMatcher(MemberSelectTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		this.expressionMatcher = factory.build(patternNode.getExpression());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof MemberSelectTree)) {
			return match.done(false);
		}
		MemberSelectTree mt = (MemberSelectTree) node;

		match.self(mt.getIdentifier().toString().equals(((MemberSelectTree) getPatternNode()).getIdentifier().toString()));
		match.child(mt.getExpression(), expressionMatcher);

		return match.done();
	}

}

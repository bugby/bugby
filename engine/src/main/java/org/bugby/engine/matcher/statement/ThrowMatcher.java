package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.ThrowTree;
import com.sun.source.tree.Tree;

public class ThrowMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher expressionMatcher;

	public ThrowMatcher(ThrowTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);

		this.expressionMatcher = factory.build(patternNode.getExpression());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof ThrowTree)) {
			return match.done(false);
		}
		ThrowTree mt = (ThrowTree) node;

		match.child(mt.getExpression(), expressionMatcher);

		return match.done();
	}

}

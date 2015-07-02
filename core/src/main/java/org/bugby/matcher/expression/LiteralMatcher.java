package org.bugby.matcher.expression;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.google.common.base.Objects;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;

public class LiteralMatcher extends DefaultTreeMatcher implements TreeMatcher {

	public LiteralMatcher(LiteralTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof LiteralTree)) {
			return match.done(false);
		}
		LiteralTree mt = (LiteralTree) node;

		match.self(Objects.equal(mt.getValue(), ((LiteralTree) getPatternNode()).getValue()));

		return match.done();
	}

}

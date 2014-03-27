package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.base.Objects;
import com.google.common.collect.Multimap;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.Tree;

public class LiteralMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final LiteralTree patternNode;

	public LiteralMatcher(LiteralTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof LiteralTree)) {
			return match.done(false);
		}
		LiteralTree mt = (LiteralTree) node;

		
		match.self(Objects.equal(mt.getValue(), patternNode.getValue()));

		return match.done();
	}

}

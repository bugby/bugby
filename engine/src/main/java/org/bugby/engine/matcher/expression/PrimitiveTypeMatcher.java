package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.PrimitiveTypeTree;
import com.sun.source.tree.Tree;

public class PrimitiveTypeMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final PrimitiveTypeTree patternNode;

	public PrimitiveTypeMatcher(PrimitiveTypeTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof PrimitiveTypeTree)) {
			return match.done(false);
		}
		PrimitiveTypeTree mt = (PrimitiveTypeTree) node;
		match.self(mt.getPrimitiveTypeKind() == patternNode.getPrimitiveTypeKind());
		return match.done();
	}
}

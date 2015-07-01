package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;

public class IdentifierVariableMatcher extends DefaultTreeMatcher implements TreeMatcher {
	public IdentifierVariableMatcher(IdentifierTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof IdentifierTree)) {
			return match.done(false);
		}
		IdentifierTree mt = (IdentifierTree) node;
		match.self(mt.getName().toString().equals(((IdentifierTree) getPatternNode()).getName().toString()));
		return match.done();
	}
}

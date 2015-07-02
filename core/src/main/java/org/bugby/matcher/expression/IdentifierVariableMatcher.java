package org.bugby.matcher.expression;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

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

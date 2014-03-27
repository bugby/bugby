package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;

public class IdentifierMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final IdentifierTree patternNode;

	public IdentifierMatcher(IdentifierTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof IdentifierTree)) {
			return match.done(false);
		}
		IdentifierTree mt = (IdentifierTree) node;

		match.self(mt.getName().toString().equals(patternNode.getName().toString()));

		return match.done();
	}

}

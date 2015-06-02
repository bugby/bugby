package org.bugby.wildcard.matcher;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class SomeFieldMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final Tree patternNode;

	public SomeFieldMatcher(Tree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof VariableTree) && !(node instanceof IdentifierTree)) {
			return match.done(false);
		}

		// TODO i should check field type and/or modifiers

		match.self(true);

		return match.done();
	}
}

package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;

public class IdentifierMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final IdentifierTree patternNode;

	public IdentifierMatcher(IdentifierTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof IdentifierTree)) {
			return HashMultimap.create();
		}
		IdentifierTree mt = (IdentifierTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchSelf(result, node, mt.getName().equals(patternNode.getName()), context);

		return result;
	}

}

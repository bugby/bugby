package org.bugby.wildcard.matcher;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class SomeFieldMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final IdentifierTree patternNode;

	public SomeFieldMatcher(IdentifierTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof VariableTree)) {
			return HashMultimap.create();
		}
		ExpressionTree mt = (ExpressionTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchSelf(result, mt, true, context);

		return result;
	}
}

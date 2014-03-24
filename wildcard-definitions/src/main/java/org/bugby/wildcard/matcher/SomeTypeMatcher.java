package org.bugby.wildcard.matcher;

import org.bugby.api.javac.TreeUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.Tree;

public class SomeTypeMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final Tree patternNode;

	public SomeTypeMatcher(Tree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof ClassTree) && !TreeUtils.isTypeTree(node)) {
			return HashMultimap.create();
		}
		Multimap<TreeMatcher, Tree> result = null;
		result = matchSelf(result, node, true, context);

		return result;
	}

	@Override
	public String toString() {
		return "SomeTypeMatcher@" + System.identityHashCode(this) + " on " + patternNode.getClass();
	}
}

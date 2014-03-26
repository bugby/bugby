package org.bugby.wildcard.matcher;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.BreakTree;
import com.sun.source.tree.ContinueTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.Tree;

public class AnyBranchMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final Tree patternNode;

	public AnyBranchMatcher(Tree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		// TODO should match intantiation blocks
		if (!(node instanceof BreakTree) && !(node instanceof ContinueTree) && !(node instanceof ReturnTree)) {
			return HashMultimap.create();
		}
		Multimap<TreeMatcher, Tree> result = null;
		result = matchSelf(result, node, true, context);

		return result;
	}

}

package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.BreakTree;
import com.sun.source.tree.Tree;

public class BreakMatcher extends DefaultMatcher implements TreeMatcher {
	private final BreakTree patternNode;

	public BreakMatcher(BreakTree patternNode) {
		this.patternNode = patternNode;
	}

	public BreakTree getPatternNode() {
		return patternNode;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof BreakTree)) {
			return HashMultimap.create();
		}
		return matchSelf(null, node, true, context);
	}

}
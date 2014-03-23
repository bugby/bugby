package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ContinueTree;
import com.sun.source.tree.Tree;

public class ContinueMatcher extends DefaultMatcher implements TreeMatcher {
	private final ContinueTree patternNode;

	public ContinueMatcher(ContinueTree patternNode) {
		this.patternNode = patternNode;
	}

	public ContinueTree getPatternNode() {
		return patternNode;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof ContinueTree)) {
			return HashMultimap.create();
		}
		return matchSelf(null, node, true, context);
	}

}

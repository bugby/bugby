package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.EmptyStatementTree;
import com.sun.source.tree.Tree;

public class EmptyStatementMatcher extends DefaultMatcher implements TreeMatcher {
	private final EmptyStatementTree patternNode;

	public EmptyStatementMatcher(EmptyStatementTree patternNode) {
		this.patternNode = patternNode;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof EmptyStatementTree)) {
			return HashMultimap.create();
		}
		return matchSelf(null, node, true, context);
	}

}

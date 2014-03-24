package org.bugby.wildcard.matcher;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;
import org.bugby.matcher.tree.MatchingType;

import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;

public class NoCodeMatcher extends DefaultTreeMatcher implements TreeMatcher {
	public NoCodeMatcher(Tree patternNode, TreeMatcherFactory factory) {
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.empty;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}

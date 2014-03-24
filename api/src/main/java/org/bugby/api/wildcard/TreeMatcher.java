package org.bugby.api.wildcard;

import org.bugby.matcher.tree.MatchingType;

import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;

public interface TreeMatcher {
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context);

	public MatchingType getMatchingType();
}

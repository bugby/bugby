package org.bugby.api.wildcard;

import com.sun.source.tree.Tree;

public interface TreeMatcher {
	public boolean matches(Tree node, MatchingContext context);

	public MatchingType getMatchingType();
}

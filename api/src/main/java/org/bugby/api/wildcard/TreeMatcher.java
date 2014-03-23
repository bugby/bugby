package org.bugby.api.wildcard;

import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;

public interface TreeMatcher {
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context);
}

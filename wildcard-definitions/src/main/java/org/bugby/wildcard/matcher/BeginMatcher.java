package org.bugby.wildcard.matcher;

import java.util.List;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;
import org.bugby.matcher.tree.MatchingType;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;

public class BeginMatcher extends DefaultTreeMatcher implements TreeMatcher {

	public BeginMatcher(Tree patternNode, TreeMatcherFactory factory) {
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.begin;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		List<Tree> list = context.getChildrenListContaining(node);
		if (list != null && list.size() > 0 && list.get(0) == node) {
			return matchSelf(null, node, true, context);
		}
		return HashMultimap.create();
	}

}

package org.bugby.engine.matcher.statement;

import java.util.List;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TryTree;

public class TryMatcher extends DefaultMatcher implements TreeMatcher {
	private final TryTree patternNode;
	private final TreeMatcher blockMatcher;
	private final List<TreeMatcher> catchMatchers;
	private final TreeMatcher finallyBlockMatcher;

	public TryMatcher(TryTree patternNode, TreeMatcher blockMatcher, List<TreeMatcher> catchMatchers, TreeMatcher finallyBlockMatcher) {
		this.patternNode = patternNode;
		this.blockMatcher = blockMatcher;
		this.catchMatchers = catchMatchers;
		this.finallyBlockMatcher = finallyBlockMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof TryTree)) {
			return HashMultimap.create();
		}
		TryTree mt = (TryTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, mt.getBlock(), blockMatcher, context);
		result = matchChild(result, node, mt.getFinallyBlock(), finallyBlockMatcher, context);
		result = matchUnorderedChildren(result, node, mt.getCatches(), catchMatchers, context);

		return result;
	}

}

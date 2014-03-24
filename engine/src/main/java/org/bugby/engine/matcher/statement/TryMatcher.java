package org.bugby.engine.matcher.statement;

import java.util.List;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TryTree;

public class TryMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TryTree patternNode;
	private final TreeMatcher blockMatcher;
	private final List<TreeMatcher> catchMatchers;
	private final TreeMatcher finallyBlockMatcher;

	public TryMatcher(TryTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.blockMatcher = factory.build(patternNode.getBlock());
		this.catchMatchers = build(factory, patternNode.getCatches());
		this.finallyBlockMatcher = factory.build(patternNode.getFinallyBlock());
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

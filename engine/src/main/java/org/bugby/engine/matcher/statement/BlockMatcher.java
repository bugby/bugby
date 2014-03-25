package org.bugby.engine.matcher.statement;

import java.util.List;

import org.bugby.api.javac.TreeUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;

public class BlockMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final BlockTree patternNode;
	private final List<TreeMatcher> statementsMatchers;

	public BlockMatcher(BlockTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.statementsMatchers = build(factory, patternNode.getStatements());
	}

	public BlockTree getPatternNode() {
		return patternNode;
	}

	public List<TreeMatcher> getStatementsMatchers() {
		return statementsMatchers;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof BlockTree)) {
			return HashMultimap.create();
		}

		return matchOrderedChildren(null, node, TreeUtils.descendantsOfType(node, StatementTree.class), statementsMatchers, context);
	}

	@Override
	public String toString() {
		return "BlockMatcher [statementsMatchers=" + statementsMatchers + "]";
	}

}

package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.Tree;

public class BinaryMatcher extends DefaultMatcher implements TreeMatcher {
	private final BinaryTree patternNode;
	private final TreeMatcher leftMatcher;
	private final TreeMatcher rightMatcher;

	public BinaryMatcher(BinaryTree patternNode, TreeMatcher leftMatcher, TreeMatcher rightMatcher) {
		this.patternNode = patternNode;
		this.leftMatcher = leftMatcher;
		this.rightMatcher = rightMatcher;
	}

	public BinaryTree getPatternNode() {
		return patternNode;
	}

	public TreeMatcher getLeftMatcher() {
		return leftMatcher;
	}

	public TreeMatcher getRightMatcher() {
		return rightMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof BinaryTree)) {
			return HashMultimap.create();
		}
		BinaryTree mt = (BinaryTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchSelf(result, node, mt.getKind().equals(patternNode.getKind()), context);
		result = matchChild(result, node, mt.getLeftOperand(), leftMatcher, context);
		result = matchChild(result, node, mt.getRightOperand(), rightMatcher, context);

		return result;
	}
}

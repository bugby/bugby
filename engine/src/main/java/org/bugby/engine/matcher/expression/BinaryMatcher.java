package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.Tree;

public class BinaryMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final BinaryTree patternNode;
	private final TreeMatcher leftMatcher;
	private final TreeMatcher rightMatcher;

	public BinaryMatcher(BinaryTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.leftMatcher = factory.build(patternNode.getLeftOperand());
		this.rightMatcher = factory.build(patternNode.getRightOperand());
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

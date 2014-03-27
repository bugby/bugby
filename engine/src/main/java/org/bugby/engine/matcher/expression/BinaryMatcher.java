package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

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
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof BinaryTree)) {
			return match.done(false);
		}
		BinaryTree mt = (BinaryTree) node;

		
		match.self(mt.getKind().equals(patternNode.getKind()));
		match.child(mt.getLeftOperand(), leftMatcher);
		match.child(mt.getRightOperand(), rightMatcher);

		return match.done();
	}
}

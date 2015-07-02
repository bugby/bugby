package org.bugby.matcher.expression;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.Tree;

public class BinaryMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher leftMatcher;
	private final TreeMatcher rightMatcher;

	public BinaryMatcher(BinaryTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		;
		this.leftMatcher = factory.build(patternNode.getLeftOperand());
		this.rightMatcher = factory.build(patternNode.getRightOperand());
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

		match.self(mt.getKind().equals(((BinaryTree) getPatternNode()).getKind()));
		match.child(mt.getLeftOperand(), leftMatcher);
		match.child(mt.getRightOperand(), rightMatcher);

		return match.done();
	}
}

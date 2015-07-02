package org.bugby.matcher.statement;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.SynchronizedTree;
import com.sun.source.tree.Tree;

public class SynchronizedMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher blockMatcher;
	private final TreeMatcher expressionMatcher;

	public SynchronizedMatcher(SynchronizedTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);

		this.blockMatcher = factory.build(patternNode.getBlock());
		this.expressionMatcher = factory.build(patternNode.getExpression());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof SynchronizedTree)) {
			return match.done(false);
		}
		SynchronizedTree ct = (SynchronizedTree) node;
		match.child(ct.getBlock(), blockMatcher);
		match.child(ct.getExpression(), expressionMatcher);

		return match.done();
	}

	@Override
	public String toString() {
		return "SynchronizedMatcher [blockMatcher=" + blockMatcher + ", expressionMatcher=" + expressionMatcher + "]";
	}

}

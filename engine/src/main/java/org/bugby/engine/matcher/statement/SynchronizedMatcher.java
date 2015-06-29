package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.SynchronizedTree;
import com.sun.source.tree.Tree;

public class SynchronizedMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final SynchronizedTree patternNode;
	private final TreeMatcher blockMatcher;
	private final TreeMatcher expressionMatcher;

	public SynchronizedMatcher(SynchronizedTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.blockMatcher = factory.build(patternNode.getBlock());
		this.expressionMatcher = factory.build(patternNode.getExpression());
	}

	public SynchronizedTree getPatternNode() {
		return patternNode;
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

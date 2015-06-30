package org.bugby.engine.matcher.statement;

import java.util.List;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.Tree;
import com.sun.source.tree.TryTree;

public class TryMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher blockMatcher;
	private final List<TreeMatcher> catchMatchers;
	private final TreeMatcher finallyBlockMatcher;

	public TryMatcher(TryTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);

		this.blockMatcher = factory.build(patternNode.getBlock());
		this.catchMatchers = build(factory, patternNode.getCatches());
		this.finallyBlockMatcher = factory.build(patternNode.getFinallyBlock());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof TryTree)) {
			return match.done(false);
		}
		TryTree mt = (TryTree) node;

		match.child(mt.getBlock(), blockMatcher);
		match.child(mt.getFinallyBlock(), finallyBlockMatcher);
		match.unorderedChildren(mt.getCatches(), catchMatchers);

		return match.done();
	}

}

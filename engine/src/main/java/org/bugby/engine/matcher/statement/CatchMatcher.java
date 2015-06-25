package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.CatchTree;
import com.sun.source.tree.Tree;

public class CatchMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher parameterMatcher;
	private final TreeMatcher blockMatcher;

	public CatchMatcher(CatchTree patternNode, TreeMatcherFactory factory) {
		this.parameterMatcher = factory.build(patternNode.getParameter());
		this.blockMatcher = factory.build(patternNode.getBlock());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof CatchTree)) {
			return match.done(false);
		}
		CatchTree ct = (CatchTree) node;

		match.child(ct.getBlock(), blockMatcher);
		match.child(ct.getParameter(), parameterMatcher);

		return match.done();

	}

}

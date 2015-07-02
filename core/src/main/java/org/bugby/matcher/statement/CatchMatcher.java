package org.bugby.matcher.statement;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.CatchTree;
import com.sun.source.tree.Tree;

public class CatchMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher parameterMatcher;
	private final TreeMatcher blockMatcher;

	public CatchMatcher(CatchTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
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

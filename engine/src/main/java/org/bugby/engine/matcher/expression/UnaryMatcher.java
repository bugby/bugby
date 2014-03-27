package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;
import com.sun.source.tree.UnaryTree;

public class UnaryMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final UnaryTree patternNode;
	private final TreeMatcher expressionMatcher;

	public UnaryMatcher(UnaryTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.expressionMatcher = factory.build(patternNode.getExpression());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof UnaryTree)) {
			return match.done(false);
		}
		UnaryTree mt = (UnaryTree) node;

		
		match.self(mt.getKind().equals(patternNode.getKind()));
		match.child(mt.getExpression(), expressionMatcher);

		return match.done();
	}

}

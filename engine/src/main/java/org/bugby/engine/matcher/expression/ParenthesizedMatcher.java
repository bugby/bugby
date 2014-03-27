package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.Multimap;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.Tree;

public class ParenthesizedMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final ParenthesizedTree patternNode;
	private final TreeMatcher expressionMatcher;

	public ParenthesizedMatcher(ParenthesizedTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.expressionMatcher = factory.build(patternNode.getExpression());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof ParenthesizedTree)) {
			return match.done(false);
		}
		ParenthesizedTree mt = (ParenthesizedTree) node;

		
		match.child(mt.getExpression(), expressionMatcher);

		return match.done();
	}

}

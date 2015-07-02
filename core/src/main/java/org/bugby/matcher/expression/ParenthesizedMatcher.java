package org.bugby.matcher.expression;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.Tree;

public class ParenthesizedMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher expressionMatcher;

	public ParenthesizedMatcher(ParenthesizedTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
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

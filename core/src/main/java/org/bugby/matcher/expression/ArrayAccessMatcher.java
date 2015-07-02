package org.bugby.matcher.expression;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.Tree;

public class ArrayAccessMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher expressionMatcher;
	private final TreeMatcher indexMatcher;

	public ArrayAccessMatcher(ArrayAccessTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		this.expressionMatcher = factory.build(patternNode.getExpression());
		this.indexMatcher = factory.build(patternNode.getIndex());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof ArrayAccessTree)) {
			return match.done(false);
		}
		ArrayAccessTree mt = (ArrayAccessTree) node;

		match.child(mt.getExpression(), expressionMatcher);
		match.child(mt.getIndex(), indexMatcher);

		return match.done();
	}

}

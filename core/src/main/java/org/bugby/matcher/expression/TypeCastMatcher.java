package org.bugby.matcher.expression;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.Tree;
import com.sun.source.tree.TypeCastTree;

public class TypeCastMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher typeMatcher;
	private final TreeMatcher expressionMatcher;

	public TypeCastMatcher(TypeCastTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		this.typeMatcher = factory.build(patternNode.getType());
		this.expressionMatcher = factory.build(patternNode.getExpression());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof TypeCastTree)) {
			return match.done(false);
		}
		TypeCastTree mt = (TypeCastTree) node;

		match.child(mt.getType(), typeMatcher);
		match.child(mt.getExpression(), expressionMatcher);

		return match.done();
	}

}

package org.bugby.wildcard.matcher.expr;

import java.util.ArrayList;
import java.util.List;

import org.bugby.api.javac.InternalUtils;
import org.bugby.api.javac.TreeUtils;
import org.bugby.api.javac.TypesUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;

public class SomeConditionUsingMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final List<TreeMatcher> argumentsMatchers;

	public SomeConditionUsingMatcher(MethodInvocationTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);

		this.argumentsMatchers = build(factory, patternNode.getArguments());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof ExpressionTree)) {
			return match.done(false);
		}
		ExpressionTree mt = (ExpressionTree) node;

		match.self(TypesUtils.isBooleanType(InternalUtils.typeOf(mt)));
		List<Tree> subexpressions = new ArrayList<Tree>();
		subexpressions.add(mt);
		subexpressions.addAll(TreeUtils.descendantsOfType(mt, Tree.class));
		match.unorderedChildren(subexpressions, argumentsMatchers);

		return match.done();
	}

}

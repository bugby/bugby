package org.bugby.matcher.wildcard.expr;

import java.util.ArrayList;
import java.util.List;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.InternalUtils;
import org.bugby.matcher.javac.TreeUtils;
import org.bugby.matcher.javac.TypesUtils;

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

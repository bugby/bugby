package org.bugby.wildcard.matcher;

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
	private final MethodInvocationTree patternNode;
	private final List<TreeMatcher> argumentsMatchers;

	public SomeConditionUsingMatcher(MethodInvocationTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
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
		match.unorderedChildren(TreeUtils.descendantsOfType(mt, Tree.class), argumentsMatchers);

		return match.done();
	}

}

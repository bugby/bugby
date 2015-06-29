package org.bugby.wildcard.matcher.expr;

import java.util.ArrayList;
import java.util.List;

import org.bugby.api.javac.TreeUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;

public class SomeExpressionUsingMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final MethodInvocationTree patternNode;
	private final List<TreeMatcher> argumentsMatchers;

	public SomeExpressionUsingMatcher(Tree patternNode, TreeMatcherFactory factory) {
		this.patternNode = removeExpressionStatement(patternNode);
		this.argumentsMatchers = build(factory, this.patternNode.getArguments());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		ExpressionTree mt = removeExpressionStatement(node);

		if (mt == null) {
			return match.done(false);
		}

		List<Tree> subexpressions = new ArrayList<Tree>();
		subexpressions.add(mt);
		subexpressions.addAll(TreeUtils.descendantsOfType(mt, Tree.class));
		match.unorderedChildren(subexpressions, argumentsMatchers);

		return match.done();
	}
}

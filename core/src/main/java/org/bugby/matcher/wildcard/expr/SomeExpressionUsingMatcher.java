package org.bugby.matcher.wildcard.expr;

import java.util.ArrayList;
import java.util.List;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;

public class SomeExpressionUsingMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final List<TreeMatcher> argumentsMatchers;

	public SomeExpressionUsingMatcher(Tree patternNode, TreeMatcherFactory factory) {
		super(removeExpressionStatement(patternNode));
		this.argumentsMatchers = build(factory, ((MethodInvocationTree) getPatternNode()).getArguments());
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

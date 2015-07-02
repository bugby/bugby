package org.bugby.matcher.wildcard.expr;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.InternalUtils;
import org.bugby.matcher.javac.TypesUtils;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;

/**
 * someCondition()
 * @author acraciun
 */
public class SomeConditionMatcher extends DefaultTreeMatcher implements TreeMatcher {

	public SomeConditionMatcher(MethodInvocationTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof ExpressionTree)) {
			return match.done(false);
		}
		ExpressionTree mt = (ExpressionTree) node;

		match.self(TypesUtils.isBooleanType(InternalUtils.typeOf(mt)));

		return match.done();
	}

}

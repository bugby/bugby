package org.bugby.wildcard.matcher;

import org.bugby.api.javac.InternalUtils;
import org.bugby.api.javac.TypesUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;

/**
 * 
 * someCondition()
 * 
 * @author acraciun
 */
public class SomeConditionMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final MethodInvocationTree patternNode;

	public SomeConditionMatcher(MethodInvocationTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
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

package org.bugby.wildcard.matcher;

import javax.lang.model.type.TypeMirror;

import org.bugby.api.javac.TreeUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;

public class SomeTypedValueMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final MethodInvocationTree patternNode;
	private final TypeMirror checkType;

	public SomeTypedValueMatcher(MethodInvocationTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		// assumes one argument is present
		checkType = TreeUtils.elementFromUse(patternNode.getArguments().get(0)).asType();
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof ExpressionTree)) {
			return match.done(false);
		}
		ExpressionTree mt = (ExpressionTree) node;

		match.self(checkType.equals(TreeUtils.elementFromUse(mt).asType()));

		return match.done();
	}

}
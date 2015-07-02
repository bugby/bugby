package org.bugby.matcher.wildcard.expr;

import javax.lang.model.type.TypeMirror;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;

public class SomeTypedValueMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TypeMirror checkType;

	public SomeTypedValueMatcher(MethodInvocationTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
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

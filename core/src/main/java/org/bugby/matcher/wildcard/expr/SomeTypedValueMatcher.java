package org.bugby.matcher.wildcard.expr;

import javax.lang.model.type.TypeMirror;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;

public class SomeTypedValueMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TypeMirror checkType;

	public SomeTypedValueMatcher(MethodInvocationTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		// assumes one argument is present - I have Type.class -> i need to get Type
		checkType = TreeUtils.elementFromUse(((MemberSelectTree) patternNode.getArguments().get(0)).getExpression()).asType();
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof ExpressionTree)) {
			return match.done(false);
		}
		ExpressionTree mt = (ExpressionTree) node;
		TypeMirror nodeType = TreeUtils.elementFromUse(mt).asType();

		match.self(context.sameType(checkType, nodeType));

		return match.done();
	}

}

package org.bugby.matcher.expression;

import java.util.List;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;

public class MethodInvocationMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher methodSelectMatcher;
	private final List<TreeMatcher> typeArgumentsMathers;
	private final List<TreeMatcher> argumentsMatchers;

	public MethodInvocationMatcher(MethodInvocationTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		this.methodSelectMatcher = factory.build(patternNode.getMethodSelect());
		this.typeArgumentsMathers = build(factory, patternNode.getTypeArguments());
		this.argumentsMatchers = build(factory, patternNode.getArguments());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof MethodInvocationTree)) {
			return match.done(false);
		}
		MethodInvocationTree mt = (MethodInvocationTree) node;

		match.child(mt.getMethodSelect(), methodSelectMatcher);
		match.orderedChildren(mt.getTypeArguments(), typeArgumentsMathers);
		match.orderedChildren(mt.getArguments(), argumentsMatchers);

		return match.done();
	}
}

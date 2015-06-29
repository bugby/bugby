package org.bugby.wildcard.matcher.method;

import java.util.List;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;

public class DynamicMethodInvocationMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final MethodInvocationTree patternNode;
	private final TreeMatcher methodSelectMatcher;
	private final List<TreeMatcher> typeArgumentsMathers;
	private final List<TreeMatcher> argumentsMatchers;

	public DynamicMethodInvocationMatcher(MethodInvocationTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
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
		//TODO here I need to check the method definition match
		match.child(mt.getMethodSelect(), methodSelectMatcher);
		match.orderedChildren(mt.getTypeArguments(), typeArgumentsMathers);
		match.orderedChildren(mt.getArguments(), argumentsMatchers);

		return match.done();
	}

}
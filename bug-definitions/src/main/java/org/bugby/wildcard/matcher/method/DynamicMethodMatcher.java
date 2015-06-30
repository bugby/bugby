package org.bugby.wildcard.matcher.method;

import java.util.List;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.MatchingPath;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;

public class DynamicMethodMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final MethodTree patternNode;
	private final TreeMatcher returnTypeMatcher;
	private final List<TreeMatcher> parametersMatchers;
	private final List<TreeMatcher> typeParametersMatchers;
	private final List<TreeMatcher> throwsMatchers;
	private final TreeMatcher bodyMatcher;

	public DynamicMethodMatcher(MethodTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.returnTypeMatcher = factory.build(patternNode.getReturnType());
		this.parametersMatchers = build(factory, patternNode.getParameters());
		this.typeParametersMatchers = build(factory, patternNode.getTypeParameters());
		this.throwsMatchers = build(factory, patternNode.getThrows());
		this.bodyMatcher = factory.build(patternNode.getBody());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof MethodTree)) {
			return match.done(false);
		}
		MethodTree mt = (MethodTree) node;

		//TODO here i need to put the name on the assignment table
		//match.self(patternNode.getName().toString().equals(mt.getName().toString()));
		List<List<MatchingPath>> paramsMatch = context.matchOrdered(parametersMatchers, mt.getParameters());
		match.self(!paramsMatch.isEmpty());

		match.unorderedChildren(mt.getThrows(), throwsMatchers);
		match.child(mt.getReturnType(), returnTypeMatcher);
		match.orderedChildren(mt.getTypeParameters(), typeParametersMatchers);
		match.child(mt.getBody(), bodyMatcher);

		return match.done();
	}

	@Override
	public String toString() {
		return "DynamicMethodMatcher [method=" + patternNode.getName() + ", returnTypeMatcher=" + returnTypeMatcher + ", parametersMatchers="
				+ parametersMatchers + ", typeParametersMatchers=" + typeParametersMatchers + ", throwsMatchers=" + throwsMatchers
				+ ", bodyMatcher=" + bodyMatcher + "]";
	}

}

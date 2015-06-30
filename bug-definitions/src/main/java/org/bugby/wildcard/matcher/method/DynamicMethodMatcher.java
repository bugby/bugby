package org.bugby.wildcard.matcher.method;

import java.util.List;
import java.util.concurrent.Callable;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.MatchingPath;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;
import org.bugby.api.wildcard.Variables;

import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;

public class DynamicMethodMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher returnTypeMatcher;
	private final List<TreeMatcher> parametersMatchers;
	private final List<TreeMatcher> typeParametersMatchers;
	private final List<TreeMatcher> throwsMatchers;
	private final TreeMatcher bodyMatcher;

	public DynamicMethodMatcher(MethodTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		this.returnTypeMatcher = factory.build(patternNode.getReturnType());
		this.parametersMatchers = build(factory, patternNode.getParameters());
		this.typeParametersMatchers = build(factory, patternNode.getTypeParameters());
		this.throwsMatchers = build(factory, patternNode.getThrows());
		this.bodyMatcher = factory.build(patternNode.getBody());
	}

	@Override
	public boolean matches(final Tree node, final MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof MethodTree)) {
			return match.done(false);
		}
		final MethodTree mt = (MethodTree) node;

		Callable<Boolean> matchSolution = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				FluidMatcher solutionMatch = matching(node, context);
				solutionMatch.unorderedChildren(mt.getThrows(), throwsMatchers);
				solutionMatch.child(mt.getReturnType(), returnTypeMatcher);
				solutionMatch.orderedChildren(mt.getTypeParameters(), typeParametersMatchers);
				solutionMatch.child(mt.getBody(), bodyMatcher);
				return solutionMatch.done();
			}
		};

		if (parametersMatchers.isEmpty()) {
			try {
				match.self(matchSolution.call());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			List<List<MatchingPath>> paramsMatch = context.matchOrdered(parametersMatchers, mt.getParameters());
			match.self(Variables.forAllVariables(context, paramsMatch, matchSolution));
		}
		return match.done();
	}

	@Override
	public String toString() {
		return "DynamicMethodMatcher [method=" + ((MethodTree) getPatternNode()).getName() + ", returnTypeMatcher=" + returnTypeMatcher
				+ ", parametersMatchers=" + parametersMatchers + ", typeParametersMatchers=" + typeParametersMatchers + ", throwsMatchers="
				+ throwsMatchers + ", bodyMatcher=" + bodyMatcher + "]";
	}

}

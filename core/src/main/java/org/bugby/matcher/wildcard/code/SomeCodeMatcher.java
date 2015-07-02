package org.bugby.matcher.wildcard.code;

import java.util.List;
import java.util.concurrent.Callable;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.MatchingPath;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.api.Variables;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;

/**
 * when used like this <br>
 * public void someCode(){ // ... bla bla // }
 * @author acraciun
 */
public class SomeCodeMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher bodyMatcher;
	private final List<TreeMatcher> parametersMatchers;

	// private final Scope patternScope;

	public SomeCodeMatcher(MethodTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		this.bodyMatcher = factory.build(patternNode.getBody());
		this.parametersMatchers = build(factory, patternNode.getParameters());
	}

	@Override
	public boolean matches(final Tree node, final MatchingContext context) {
		FluidMatcher match = matching(node, context);
		// TODO should match intantiation blocks
		if (!(node instanceof MethodTree)) {
			return match.done(false);
		}
		final MethodTree mt = (MethodTree) node;

		Callable<Boolean> matchSolution = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				FluidMatcher solutionMatch = partialMatching(node, context);
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
			//TODO probably I should match with the wrappers of the variable's types instead of the variables themselves!
			List<List<MatchingPath>> paramsMatch = context.matchUnordered(parametersMatchers, Variables.extractAllVariables(mt));
			match.self(Variables.forAllVariables(context, paramsMatch, matchSolution));
		}

		return match.done();
	}

	@Override
	public String toString() {
		return "SomeCodeMatcher [patternNode=" + getPatternNode() + ", bodyMatcher=" + bodyMatcher + "]";
	}

}

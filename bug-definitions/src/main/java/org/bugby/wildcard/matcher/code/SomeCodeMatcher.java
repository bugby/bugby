package org.bugby.wildcard.matcher.code;

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
				FluidMatcher solutionMatch = matching(node, context);
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

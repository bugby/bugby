package org.bugby.engine.matcher.statement;

import java.util.List;
import java.util.concurrent.Callable;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.MatchingPath;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;
import org.bugby.api.wildcard.Variables;

import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.Tree;

public class ForLoopMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final List<TreeMatcher> initializerMatchers;
	private final TreeMatcher conditionMatcher;
	private final List<TreeMatcher> updateMatchers;
	private final TreeMatcher statementMatcher;

	public ForLoopMatcher(ForLoopTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);

		this.initializerMatchers = build(factory, patternNode.getInitializer());
		this.conditionMatcher = factory.build(patternNode.getCondition());
		this.updateMatchers = build(factory, patternNode.getUpdate());
		this.statementMatcher = factory.build(patternNode.getStatement());
	}

	@Override
	public boolean matches(final Tree node, final MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof ForLoopTree)) {
			return match.done(false);
		}
		final ForLoopTree ct = (ForLoopTree) node;

		Callable<Boolean> matchSolution = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				FluidMatcher solutionMatch = partialMatching(node, context);
				solutionMatch.unorderedChildren(ct.getUpdate(), updateMatchers);
				solutionMatch.child(ct.getCondition(), conditionMatcher);
				solutionMatch.child(ct.getStatement(), statementMatcher);
				return solutionMatch.done();
			}
		};

		if (initializerMatchers.isEmpty()) {
			try {
				match.self(matchSolution.call());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			List<List<MatchingPath>> varsMatch = context.matchUnordered(initializerMatchers, ct.getInitializer());
			match.self(Variables.forAllVariables(context, varsMatch, matchSolution));
		}

		return match.done();
	}

}

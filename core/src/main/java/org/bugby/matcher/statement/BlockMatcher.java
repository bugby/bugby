package org.bugby.matcher.statement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.MatchingPath;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.api.Variables;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class BlockMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final List<TreeMatcher> statementsMatchers;
	private final List<TreeMatcher> variablesMatchers;

	public BlockMatcher(BlockTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		this.statementsMatchers = build(factory, filterOutVariables(patternNode.getStatements()));
		this.variablesMatchers = build(factory, TreeUtils.descendantsOfType(patternNode, VariableTree.class));
	}

	private List<StatementTree> filterOutVariables(List<? extends StatementTree> statements) {
		List<StatementTree> result = new ArrayList<StatementTree>(statements.size());
		for (StatementTree s : statements) {
			if (!(s instanceof VariableTree)) {
				result.add(s);
			}
		}
		return result;
	}

	public List<TreeMatcher> getStatementsMatchers() {
		return statementsMatchers;
	}

	@Override
	public boolean matches(final Tree node, final MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof BlockTree)) {
			return match.done(false);
		}

		List<VariableTree> varDeclarations = TreeUtils.descendantsOfType(node, VariableTree.class);
		//		final List<StatementTree> statements = filterOutVariables(TreeUtils.descendantsOfType(node, StatementTree.class));
		//TODO not sure i should keep or not the variable declarations !? because it may contains needed expressions in the initializers
		final List<StatementTree> statements = TreeUtils.descendantsOfType(node, StatementTree.class);

		Callable<Boolean> matchSolution = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				FluidMatcher solutionMatch = partialMatching(node, context);
				solutionMatch.orderedChildren(statements, statementsMatchers);
				return solutionMatch.done();
			}
		};

		if (variablesMatchers.isEmpty()) {
			try {
				match.self(matchSolution.call());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			List<List<MatchingPath>> varsMatch = context.matchUnordered(variablesMatchers, varDeclarations);
			match.self(Variables.forAllVariables(context, varsMatch, matchSolution));
		}

		return match.done();
	}

	@Override
	public String toString() {
		return "BlockMatcher [statementsMatchers=" + statementsMatchers + "]";
	}

}

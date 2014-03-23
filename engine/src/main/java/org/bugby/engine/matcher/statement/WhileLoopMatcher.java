package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;
import com.sun.source.tree.WhileLoopTree;

public class WhileLoopMatcher extends DefaultMatcher implements TreeMatcher {
	private final WhileLoopTree patternNode;
	private final TreeMatcher conditionMatcher;
	private final TreeMatcher statementMatcher;

	public WhileLoopMatcher(WhileLoopTree patternNode, TreeMatcher conditionMatcher, TreeMatcher statementMatcher) {
		this.patternNode = patternNode;
		this.conditionMatcher = conditionMatcher;
		this.statementMatcher = statementMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof WhileLoopTree)) {
			return HashMultimap.create();
		}
		WhileLoopTree ct = (WhileLoopTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, ct.getCondition(), conditionMatcher, context);
		result = matchChild(result, node, ct.getStatement(), statementMatcher, context);

		return result;
	}

}

package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.DoWhileLoopTree;
import com.sun.source.tree.Tree;

public class DoWhileLoopMatcher extends DefaultMatcher implements TreeMatcher {
	private final DoWhileLoopTree patternNode;
	private final TreeMatcher conditionMatcher;
	private final TreeMatcher statementMatcher;

	public DoWhileLoopMatcher(DoWhileLoopTree patternNode, TreeMatcher conditionMatcher, TreeMatcher statementMatcher) {
		this.patternNode = patternNode;
		this.conditionMatcher = conditionMatcher;
		this.statementMatcher = statementMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof DoWhileLoopTree)) {
			return HashMultimap.create();
		}
		DoWhileLoopTree ct = (DoWhileLoopTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, ct.getCondition(), conditionMatcher, context);
		result = matchChild(result, node, ct.getStatement(), statementMatcher, context);

		return result;
	}

}

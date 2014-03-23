package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.LabeledStatementTree;
import com.sun.source.tree.Tree;

public class LabeledMatcher extends DefaultMatcher implements TreeMatcher {
	private final LabeledStatementTree patternNode;
	private final TreeMatcher statementMatcher;

	public LabeledMatcher(LabeledStatementTree patternNode, TreeMatcher statementMatcher) {
		this.patternNode = patternNode;
		this.statementMatcher = statementMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof LabeledStatementTree)) {
			return HashMultimap.create();
		}
		LabeledStatementTree mt = (LabeledStatementTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchSelf(result, node, patternNode.getLabel().equals(mt.getLabel()), context);
		result = matchChild(result, node, mt.getStatement(), statementMatcher, context);

		return result;
	}

}

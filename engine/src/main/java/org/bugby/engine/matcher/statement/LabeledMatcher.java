package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.LabeledStatementTree;
import com.sun.source.tree.Tree;

public class LabeledMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final LabeledStatementTree patternNode;
	private final TreeMatcher statementMatcher;

	public LabeledMatcher(LabeledStatementTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.statementMatcher = factory.build(patternNode.getStatement());
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

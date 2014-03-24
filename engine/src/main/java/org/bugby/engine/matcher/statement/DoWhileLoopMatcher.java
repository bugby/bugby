package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.DoWhileLoopTree;
import com.sun.source.tree.Tree;

public class DoWhileLoopMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final DoWhileLoopTree patternNode;
	private final TreeMatcher conditionMatcher;
	private final TreeMatcher statementMatcher;

	public DoWhileLoopMatcher(DoWhileLoopTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.conditionMatcher = factory.build(patternNode.getCondition());
		this.statementMatcher = factory.build(patternNode.getStatement());
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

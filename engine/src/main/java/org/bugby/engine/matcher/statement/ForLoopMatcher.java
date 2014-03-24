package org.bugby.engine.matcher.statement;

import java.util.List;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.Tree;

public class ForLoopMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final ForLoopTree patternNode;
	private final List<TreeMatcher> initializerMatchers;
	private final TreeMatcher conditionMatcher;
	private final List<TreeMatcher> updateMatchers;
	private final TreeMatcher statementMatcher;

	public ForLoopMatcher(ForLoopTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.initializerMatchers = build(factory, patternNode.getInitializer());
		this.conditionMatcher = factory.build(patternNode.getCondition());
		this.updateMatchers = build(factory, patternNode.getUpdate());
		this.statementMatcher = factory.build(patternNode.getStatement());
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof ForLoopTree)) {
			return HashMultimap.create();
		}
		ForLoopTree ct = (ForLoopTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchUnorderedChildren(result, node, ct.getInitializer(), initializerMatchers, context);
		result = matchUnorderedChildren(result, node, ct.getUpdate(), updateMatchers, context);
		result = matchChild(result, node, ct.getCondition(), conditionMatcher, context);
		result = matchChild(result, node, ct.getStatement(), statementMatcher, context);

		return result;
	}

}

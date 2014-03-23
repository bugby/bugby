package org.bugby.engine.matcher.statement;

import java.util.List;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.Tree;

public class ForLoopMatcher extends DefaultMatcher implements TreeMatcher {
	private final ForLoopTree patternNode;
	private final List<TreeMatcher> initializerMatchers;
	private final TreeMatcher conditionMatcher;
	private final List<TreeMatcher> updateMatchers;
	private final TreeMatcher statementMatcher;

	public ForLoopMatcher(ForLoopTree patternNode, List<TreeMatcher> initializerMatchers, TreeMatcher conditionMatcher,
			List<TreeMatcher> updateMatchers, TreeMatcher statementMatcher) {
		this.patternNode = patternNode;
		this.initializerMatchers = initializerMatchers;
		this.conditionMatcher = conditionMatcher;
		this.updateMatchers = updateMatchers;
		this.statementMatcher = statementMatcher;
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

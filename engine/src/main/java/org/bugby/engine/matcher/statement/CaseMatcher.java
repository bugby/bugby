package org.bugby.engine.matcher.statement;

import java.util.List;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.CaseTree;
import com.sun.source.tree.Tree;

public class CaseMatcher extends DefaultMatcher implements TreeMatcher {
	private final CaseTree patternNode;
	private final TreeMatcher expressionMatcher;
	private final List<TreeMatcher> statementsMatchers;

	public CaseMatcher(CaseTree patternNode, TreeMatcher expressionMatcher, List<TreeMatcher> statementsMatchers) {
		this.patternNode = patternNode;
		this.expressionMatcher = expressionMatcher;
		this.statementsMatchers = statementsMatchers;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof CaseTree)) {
			return HashMultimap.create();
		}
		CaseTree ct = (CaseTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, ct.getExpression(), expressionMatcher, context);
		result = matchOrderedChildren(result, node, ct.getStatements(), statementsMatchers, context);

		return result;

	}

}

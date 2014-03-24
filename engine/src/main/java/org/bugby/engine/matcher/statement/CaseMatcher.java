package org.bugby.engine.matcher.statement;

import java.util.List;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.CaseTree;
import com.sun.source.tree.Tree;

public class CaseMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final CaseTree patternNode;
	private final TreeMatcher expressionMatcher;
	private final List<TreeMatcher> statementsMatchers;

	public CaseMatcher(CaseTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.expressionMatcher = factory.build(patternNode.getExpression());
		this.statementsMatchers = build(factory, patternNode.getStatements());
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

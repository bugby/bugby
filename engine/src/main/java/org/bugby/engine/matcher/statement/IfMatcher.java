package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.IfTree;
import com.sun.source.tree.Tree;

public class IfMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final IfTree patternNode;
	private final TreeMatcher conditionMatcher;
	private final TreeMatcher thenMatcher;
	private final TreeMatcher elseMatcher;

	public IfMatcher(IfTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.conditionMatcher = factory.build(patternNode.getCondition());
		this.thenMatcher = factory.build(patternNode.getThenStatement());
		this.elseMatcher = factory.build(patternNode.getElseStatement());
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof IfTree)) {
			return HashMultimap.create();
		}
		IfTree ct = (IfTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, ct.getCondition(), conditionMatcher, context);
		result = matchChild(result, node, ct.getThenStatement(), thenMatcher, context);
		result = matchChild(result, node, ct.getElseStatement(), elseMatcher, context);

		return result;
	}

}

package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.Tree;

public class ReturnMatcher extends DefaultMatcher implements TreeMatcher {
	private final ReturnTree patternNode;
	private final TreeMatcher expressionMatcher;

	public ReturnMatcher(ReturnTree patternNode, TreeMatcher expressionMatcher) {
		this.patternNode = patternNode;
		this.expressionMatcher = expressionMatcher;
	}

	public ReturnTree getPatternNode() {
		return patternNode;
	}

	public TreeMatcher getExpressionMatcher() {
		return expressionMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof ReturnTree)) {
			return HashMultimap.create();
		}
		ReturnTree mt = (ReturnTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, mt.getExpression(), expressionMatcher, context);

		return result;
	}

}

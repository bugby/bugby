package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.Tree;

public class ParanthesizedMatcher extends DefaultMatcher implements TreeMatcher {
	private final ParenthesizedTree patternNode;
	private final TreeMatcher expressionMatcher;

	public ParanthesizedMatcher(ParenthesizedTree patternNode, TreeMatcher expressionMatcher) {
		this.patternNode = patternNode;
		this.expressionMatcher = expressionMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof ParenthesizedTree)) {
			return HashMultimap.create();
		}
		ParenthesizedTree mt = (ParenthesizedTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, mt.getExpression(), expressionMatcher, context);

		return result;
	}

}

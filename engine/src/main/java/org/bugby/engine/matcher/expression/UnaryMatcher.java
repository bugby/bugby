package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;
import com.sun.source.tree.UnaryTree;

public class UnaryMatcher extends DefaultMatcher implements TreeMatcher {
	private final UnaryTree patternNode;
	private final TreeMatcher expressionMatcher;

	public UnaryMatcher(UnaryTree patternNode, TreeMatcher expressionMatcher) {
		this.patternNode = patternNode;
		this.expressionMatcher = expressionMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof UnaryTree)) {
			return HashMultimap.create();
		}
		UnaryTree mt = (UnaryTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchSelf(result, node, mt.getKind().equals(patternNode.getKind()), context);
		result = matchChild(result, node, mt.getExpression(), expressionMatcher, context);

		return result;
	}

}

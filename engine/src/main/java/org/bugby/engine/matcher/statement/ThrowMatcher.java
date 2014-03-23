package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ThrowTree;
import com.sun.source.tree.Tree;

public class ThrowMatcher extends DefaultMatcher implements TreeMatcher {
	private final ThrowTree patternNode;
	private final TreeMatcher expressionMatcher;

	public ThrowMatcher(ThrowTree patternNode, TreeMatcher expressionMatcher) {
		this.patternNode = patternNode;
		this.expressionMatcher = expressionMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof ThrowTree)) {
			return HashMultimap.create();
		}
		ThrowTree mt = (ThrowTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, mt.getExpression(), expressionMatcher, context);

		return result;
	}

}

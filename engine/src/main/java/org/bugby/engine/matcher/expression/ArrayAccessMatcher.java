package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.Tree;

public class ArrayAccessMatcher extends DefaultMatcher implements TreeMatcher {
	private final ArrayAccessTree patternNode;
	private final TreeMatcher expressionMatcher;
	private final TreeMatcher indexMatcher;

	public ArrayAccessMatcher(ArrayAccessTree patternNode, TreeMatcher expressionMatcher, TreeMatcher indexMatcher) {
		this.patternNode = patternNode;
		this.expressionMatcher = expressionMatcher;
		this.indexMatcher = indexMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof ArrayAccessTree)) {
			return HashMultimap.create();
		}
		ArrayAccessTree mt = (ArrayAccessTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, mt.getExpression(), expressionMatcher, context);
		result = matchChild(result, node, mt.getIndex(), indexMatcher, context);

		return result;
	}

}

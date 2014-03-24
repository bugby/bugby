package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.Tree;

public class ArrayAccessMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final ArrayAccessTree patternNode;
	private final TreeMatcher expressionMatcher;
	private final TreeMatcher indexMatcher;

	public ArrayAccessMatcher(ArrayAccessTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.expressionMatcher = factory.build(patternNode.getExpression());
		this.indexMatcher = factory.build(patternNode.getIndex());
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

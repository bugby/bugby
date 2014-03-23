package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TypeCastTree;

public class TypeCastMatcher extends DefaultMatcher implements TreeMatcher {
	private final TypeCastTree patternNode;
	private final TreeMatcher typeMatcher;
	private final TreeMatcher expressionMatcher;

	public TypeCastMatcher(TypeCastTree patternNode, TreeMatcher typeMatcher, TreeMatcher expressionMatcher) {
		this.patternNode = patternNode;
		this.typeMatcher = typeMatcher;
		this.expressionMatcher = expressionMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof TypeCastTree)) {
			return HashMultimap.create();
		}
		TypeCastTree mt = (TypeCastTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, mt.getType(), typeMatcher, context);
		result = matchChild(result, node, mt.getExpression(), expressionMatcher, context);

		return result;
	}

}

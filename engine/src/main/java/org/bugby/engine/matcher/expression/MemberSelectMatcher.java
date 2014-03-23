package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.Tree;

public class MemberSelectMatcher extends DefaultMatcher implements TreeMatcher {
	private final MemberSelectTree patternNode;
	private final TreeMatcher expressionMatcher;

	public MemberSelectMatcher(MemberSelectTree patternNode, TreeMatcher expressionMatcher) {
		this.patternNode = patternNode;
		this.expressionMatcher = expressionMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof MemberSelectTree)) {
			return HashMultimap.create();
		}
		MemberSelectTree mt = (MemberSelectTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchSelf(result, node, mt.getIdentifier().equals(patternNode.getIdentifier()), context);
		result = matchChild(result, node, mt.getExpression(), expressionMatcher, context);

		return result;
	}

}

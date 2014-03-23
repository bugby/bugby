package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.CompoundAssignmentTree;
import com.sun.source.tree.Tree;

public class CompoundAssignmentMatcher extends DefaultMatcher implements TreeMatcher {
	private final CompoundAssignmentTree patternNode;
	private final TreeMatcher variableMatcher;
	private final TreeMatcher expressionMatcher;

	public CompoundAssignmentMatcher(CompoundAssignmentTree patternNode, TreeMatcher variableMatcher, TreeMatcher expressionMatcher) {
		this.patternNode = patternNode;
		this.variableMatcher = variableMatcher;
		this.expressionMatcher = expressionMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof CompoundAssignmentTree)) {
			return HashMultimap.create();
		}
		CompoundAssignmentTree mt = (CompoundAssignmentTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchSelf(result, node, mt.getKind().equals(patternNode.getKind()), context);
		result = matchChild(result, node, mt.getExpression(), expressionMatcher, context);
		result = matchChild(result, node, mt.getVariable(), variableMatcher, context);

		return result;
	}
}

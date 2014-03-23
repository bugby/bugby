package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class VariableMatcher extends DefaultMatcher implements TreeMatcher {
	private final VariableTree patternNode;
	private final TreeMatcher typeMatcher;
	private final TreeMatcher initMatcher;

	public VariableMatcher(VariableTree patternNode, TreeMatcher typeMatcher, TreeMatcher initMatcher) {
		this.patternNode = patternNode;
		this.typeMatcher = typeMatcher;
		this.initMatcher = initMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof VariableTree)) {
			return HashMultimap.create();
		}
		VariableTree mt = (VariableTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchSelf(result, node, mt.getName().equals(patternNode.getName()), context);
		result = matchChild(result, node, mt.getType(), typeMatcher, context);
		result = matchChild(result, node, mt.getInitializer(), initMatcher, context);

		return result;
	}

}

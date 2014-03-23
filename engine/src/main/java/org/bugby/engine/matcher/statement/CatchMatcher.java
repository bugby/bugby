package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.CaseTree;
import com.sun.source.tree.CatchTree;
import com.sun.source.tree.Tree;

public class CatchMatcher extends DefaultMatcher implements TreeMatcher {
	private final CatchTree patternNode;
	private final TreeMatcher parameterMatcher;
	private final TreeMatcher blockMatcher;

	public CatchMatcher(CatchTree patternNode, TreeMatcher parameterMatcher, TreeMatcher blockMatcher) {
		this.patternNode = patternNode;
		this.parameterMatcher = parameterMatcher;
		this.blockMatcher = blockMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof CaseTree)) {
			return HashMultimap.create();
		}
		CatchTree ct = (CatchTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, ct.getBlock(), blockMatcher, context);
		result = matchChild(result, node, ct.getParameter(), parameterMatcher, context);

		return result;

	}

}

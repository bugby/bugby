package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.CaseTree;
import com.sun.source.tree.CatchTree;
import com.sun.source.tree.Tree;

public class CatchMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final CatchTree patternNode;
	private final TreeMatcher parameterMatcher;
	private final TreeMatcher blockMatcher;

	public CatchMatcher(CatchTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.parameterMatcher = factory.build(patternNode.getParameter());
		this.blockMatcher = factory.build(patternNode.getBlock());
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

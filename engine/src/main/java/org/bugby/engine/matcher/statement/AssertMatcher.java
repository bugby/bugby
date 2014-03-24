package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.AssertTree;
import com.sun.source.tree.Tree;

public class AssertMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final AssertTree patternNode;
	private final TreeMatcher conditionMatcher;
	private final TreeMatcher detailMatcher;

	public AssertMatcher(AssertTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.conditionMatcher = factory.build(patternNode.getCondition());
		this.detailMatcher = factory.build(patternNode.getDetail());
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof AssertTree)) {
			return HashMultimap.create();
		}
		AssertTree mt = (AssertTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, mt.getCondition(), conditionMatcher, context);
		result = matchChild(result, node, mt.getDetail(), detailMatcher, context);

		return result;
	}

}

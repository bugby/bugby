package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.InstanceOfTree;
import com.sun.source.tree.Tree;

public class InstanceofMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final InstanceOfTree patternNode;
	private final TreeMatcher typeMatcher;
	private final TreeMatcher expressionMatcher;

	public InstanceofMatcher(InstanceOfTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.typeMatcher = factory.build(patternNode.getType());
		this.expressionMatcher = factory.build(patternNode.getExpression());
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof InstanceOfTree)) {
			return HashMultimap.create();
		}
		InstanceOfTree mt = (InstanceOfTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, mt.getType(), typeMatcher, context);
		result = matchChild(result, node, mt.getExpression(), expressionMatcher, context);

		return result;
	}

}

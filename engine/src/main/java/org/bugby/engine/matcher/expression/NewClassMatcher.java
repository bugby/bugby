package org.bugby.engine.matcher.expression;

import java.util.List;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.Tree;

public class NewClassMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final NewClassTree patternNode;
	private final TreeMatcher enclosingExpressionMatcher;
	private final List<TreeMatcher> typeArgumentsMatchers;
	private final TreeMatcher identifierMatcher;
	private final List<TreeMatcher> argumentsMatchers;
	private final TreeMatcher classBodyMatcher;

	public NewClassMatcher(NewClassTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.enclosingExpressionMatcher = factory.build(patternNode.getEnclosingExpression());
		this.typeArgumentsMatchers = build(factory, patternNode.getTypeArguments());
		this.identifierMatcher = factory.build(patternNode.getIdentifier());
		this.argumentsMatchers = build(factory, patternNode.getArguments());
		this.classBodyMatcher = factory.build(patternNode.getClassBody());
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof NewClassTree)) {
			return HashMultimap.create();
		}
		NewClassTree mt = (NewClassTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, mt.getEnclosingExpression(), enclosingExpressionMatcher, context);
		result = matchChild(result, node, mt.getIdentifier(), identifierMatcher, context);
		result = matchChild(result, node, mt.getClassBody(), classBodyMatcher, context);
		result = matchOrderedChildren(result, node, mt.getTypeArguments(), typeArgumentsMatchers, context);
		result = matchOrderedChildren(result, node, mt.getArguments(), argumentsMatchers, context);

		return result;
	}

}

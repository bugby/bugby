package org.bugby.engine.matcher.expression;

import java.util.List;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.Tree;

public class NewClassMatcher extends DefaultMatcher implements TreeMatcher {
	private final NewClassTree patternNode;
	private final TreeMatcher enclosingExpressionMatcher;
	private final List<TreeMatcher> typeArgumentsMathers;
	private final TreeMatcher identifierMatcher;
	private final List<TreeMatcher> argumentsMatchers;
	private final TreeMatcher classBodyMatcher;

	public NewClassMatcher(NewClassTree patternNode, TreeMatcher enclosingExpressionMatcher, List<TreeMatcher> typeArgumentsMathers,
			TreeMatcher identifierMatcher, List<TreeMatcher> argumentsMatchers, TreeMatcher classBodyMatcher) {
		this.patternNode = patternNode;
		this.enclosingExpressionMatcher = enclosingExpressionMatcher;
		this.typeArgumentsMathers = typeArgumentsMathers;
		this.identifierMatcher = identifierMatcher;
		this.argumentsMatchers = argumentsMatchers;
		this.classBodyMatcher = classBodyMatcher;
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
		result = matchOrderedChildren(result, node, mt.getTypeArguments(), typeArgumentsMathers, context);
		result = matchOrderedChildren(result, node, mt.getArguments(), argumentsMatchers, context);

		return result;
	}

}

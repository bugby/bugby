package org.bugby.engine.matcher.expression;

import java.util.List;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.Tree;

public class NewArrayMatcher extends DefaultMatcher implements TreeMatcher {
	private final NewArrayTree patternNode;
	private final TreeMatcher typeMatcher;
	private final List<TreeMatcher> dimensionsMatchers;
	private final List<TreeMatcher> initializersMatchers;

	public NewArrayMatcher(NewArrayTree patternNode, TreeMatcher typeMatcher, List<TreeMatcher> dimensionsMatchers,
			List<TreeMatcher> initializersMatchers) {
		this.patternNode = patternNode;
		this.typeMatcher = typeMatcher;
		this.dimensionsMatchers = dimensionsMatchers;
		this.initializersMatchers = initializersMatchers;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof NewArrayTree)) {
			return HashMultimap.create();
		}
		NewArrayTree mt = (NewArrayTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, mt.getType(), typeMatcher, context);
		result = matchOrderedChildren(result, node, mt.getDimensions(), dimensionsMatchers, context);
		result = matchOrderedChildren(result, node, mt.getInitializers(), initializersMatchers, context);

		return result;
	}

}

package org.bugby.engine.matcher.declaration;

import java.util.List;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;

public class MethodMatcher extends DefaultMatcher implements TreeMatcher {
	private final MethodTree patternNode;
	private final TreeMatcher returnTypeMatcher;
	private final List<TreeMatcher> parametersMatchers;
	private final List<TreeMatcher> typeParametersMatchers;
	private final List<TreeMatcher> throwsMatchers;
	private final TreeMatcher bodyMatcher;

	public MethodMatcher(MethodTree patternNode, TreeMatcher returnTypeMatcher, List<TreeMatcher> parametersMatchers,
			List<TreeMatcher> typeParametersMatchers, List<TreeMatcher> throwsMatchers, TreeMatcher bodyMatcher) {
		this.patternNode = patternNode;
		this.returnTypeMatcher = returnTypeMatcher;
		this.parametersMatchers = parametersMatchers;
		this.typeParametersMatchers = typeParametersMatchers;
		this.throwsMatchers = throwsMatchers;
		this.bodyMatcher = bodyMatcher;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof MethodTree)) {
			return HashMultimap.create();
		}
		MethodTree mt = (MethodTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchSelf(result, node, patternNode.getName().equals(mt.getName()), context);
		result = matchUnorderedChildren(result, node, mt.getThrows(), throwsMatchers, context);
		result = matchChild(result, node, mt.getReturnType(), returnTypeMatcher, context);
		result = matchOrderedChildren(result, node, mt.getParameters(), parametersMatchers, context);
		result = matchOrderedChildren(result, node, mt.getTypeParameters(), typeParametersMatchers, context);
		result = matchChild(result, node, mt.getBody(), bodyMatcher, context);

		return result;
	}

}

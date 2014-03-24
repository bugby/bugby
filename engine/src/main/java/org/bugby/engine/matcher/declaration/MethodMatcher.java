package org.bugby.engine.matcher.declaration;

import java.util.List;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;

public class MethodMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final MethodTree patternNode;
	private final TreeMatcher returnTypeMatcher;
	private final List<TreeMatcher> parametersMatchers;
	private final List<TreeMatcher> typeParametersMatchers;
	private final List<TreeMatcher> throwsMatchers;
	private final TreeMatcher bodyMatcher;

	public MethodMatcher(MethodTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.returnTypeMatcher = factory.build(patternNode.getReturnType());
		this.parametersMatchers = build(factory, patternNode.getParameters());
		this.typeParametersMatchers = build(factory, patternNode.getTypeParameters());
		this.throwsMatchers = build(factory, patternNode.getThrows());
		this.bodyMatcher = factory.build(patternNode.getBody());
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

package org.bugby.api.wildcard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bugby.matcher.tree.MatchingType;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;

abstract public class DefaultTreeMatcher implements TreeMatcher {

	protected <T> List<T> list(T element) {
		return element == null ? Collections.<T>emptyList() : Collections.singletonList(element);
	}

	protected Multimap<TreeMatcher, Tree> matchSelf(Multimap<TreeMatcher, Tree> currentMatch, Tree parent, boolean condition,
			MatchingContext context) {
		if (!condition) {
			return HashMultimap.create();
		}

		Multimap<TreeMatcher, Tree> result = currentMatch != null ? currentMatch : HashMultimap.<TreeMatcher, Tree>create();
		if (!result.containsEntry(this, parent)) {
			result.put(this, parent);
		}
		return result;

	}

	protected Multimap<TreeMatcher, Tree> matchChild(Multimap<TreeMatcher, Tree> currentMatch, Tree parent, Tree child, TreeMatcher matcher,
			MatchingContext context) {
		return matchChildren(currentMatch, parent, list(child), list(matcher), context, false);
	}

	protected Multimap<TreeMatcher, Tree> matchUnorderedChildren(Multimap<TreeMatcher, Tree> currentMatch, Tree parent,
			List<? extends Tree> children, List<TreeMatcher> matchers, MatchingContext context) {
		return matchChildren(currentMatch, parent, children, matchers, context, false);
	}

	protected Multimap<TreeMatcher, Tree> matchOrderedChildren(Multimap<TreeMatcher, Tree> currentMatch, Tree parent,
			List<? extends Tree> children, List<TreeMatcher> matchers, MatchingContext context) {
		return matchChildren(currentMatch, parent, children, matchers, context, true);
	}

	private Multimap<TreeMatcher, Tree> matchChildren(Multimap<TreeMatcher, Tree> currentMatch, Tree parent, List<? extends Tree> children,
			List<TreeMatcher> matchers, MatchingContext context, boolean ordered) {
		if (currentMatch != null && currentMatch.isEmpty()) {
			// this means a match already failed - propagate it until the end
			return currentMatch;
		}

		if (matchers.isEmpty()) {
			// nothing to be matched
			Multimap<TreeMatcher, Tree> result = currentMatch != null ? currentMatch : HashMultimap.<TreeMatcher, Tree>create();
			if (!result.containsEntry(this, parent)) {
				result.put(this, parent);
			}
			return result;
		}

		Multimap<TreeMatcher, Tree> childrenMatch = ordered ? context.matchOrdered(matchers, children) : context.matchUnordered(matchers,
				children);
		if (childrenMatch.isEmpty()) {
			return HashMultimap.create();
		}
		if (!childrenMatch.containsEntry(this, parent)) {
			childrenMatch.put(this, parent);
		}
		if (currentMatch == null) {
			return childrenMatch;
		}
		currentMatch.putAll(childrenMatch);
		return currentMatch;
	}

	public static List<TreeMatcher> build(TreeMatcherFactory factory, List<? extends Tree> nodes) {
		if (nodes.isEmpty()) {
			return Collections.emptyList();
		}
		List<TreeMatcher> matchers = new ArrayList<TreeMatcher>(nodes.size());
		for (Tree node : nodes) {
			TreeMatcher matcher = factory.build(node);
			if (matcher != null) {
				matchers.add(matcher);
			}
		}
		return matchers;
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.normal;
	}

	// private final long id = ids.incrementAndGet();
	// private final Map<String, MethodCallExpr> patternAnnotations;
	//
	// public DefaultNodeMatcher(Node targetNode, Map<String, MethodCallExpr> patternAnnotations) {
	// this.targetNode = targetNode;
	// this.patternAnnotations = patternAnnotations;
	// }
	//
	// @Override
	// public boolean matches(TreeModel<Node, Node> treeModel, Node node, MatchingContext context) {
	// if (!node.getClass().equals(targetNode.getClass())) {
	// return false;
	// }
	// if (patternAnnotations.containsKey("$last") && !treeModel.isLastChild(node)) {
	// return false;
	// }
	//
	// return ASTModelBridges.getBridge(targetNode).areSimilar(targetNode, node);
	// }
}

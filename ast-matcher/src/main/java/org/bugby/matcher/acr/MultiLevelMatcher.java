package org.bugby.matcher.acr;

import java.util.Collections;
import java.util.List;

import org.bugby.matcher.tree.Tree;

public class MultiLevelMatcher<T, W> {
	private final NodeMatch<T, W> nodeMatch;
	private final OneLevelMatcher<Tree<T>, Tree<W>> oneLevelMatcher;

	public MultiLevelMatcher(NodeMatch<T, W> nodeMatch) {
		this.nodeMatch = nodeMatch;
		NodeMatch<Tree<T>, Tree<W>> treeNodeMatch = new NodeMatch<Tree<T>, Tree<W>>() {
			@Override
			public boolean match(Tree<W> wildcard, Tree<T> node) {
				// match first the node itself
				boolean ok = MultiLevelMatcher.this.nodeMatch.match(wildcard.getValue(), node.getValue());
				if (!ok) {
					return false;
				}
				if (wildcard.getChildrenCount() != 0) {
					// if the wildcard has children continue the matching to the children
					return !oneLevelMatcher.matchOrdered(node.getChildren(), wildcard.getChildren()).isEmpty();
				}
				return true;
			}
		};
		oneLevelMatcher = new OneLevelMatcher<Tree<T>, Tree<W>>(treeNodeMatch);
	}

	public boolean match(Tree<T> parentNode, Tree<W> wildcardParent) {
		List<Tree<T>> initialNodes = Collections.singletonList(parentNode);
		List<Tree<W>> initialWildcards = Collections.singletonList(wildcardParent);
		List<List<Tree<T>>> match = oneLevelMatcher.matchOrdered(initialNodes, initialWildcards);
		return !match.isEmpty();
	}
}

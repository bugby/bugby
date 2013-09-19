package org.bugby.matcher.acr;

import java.util.Collections;
import java.util.List;

/**
 * 
 * @author acraciun
 * 
 * @param <T>
 *            - the type of the source nodes
 * @param <W>
 *            - the type of the wildcards
 * @param <TT>
 *            - the type of the tree storing the nodes
 * @param <TW>
 *            - the type of the tree storing the wildcards
 */
public class MultiLevelMatcher<T, W, TT, TW> {
	private final NodeMatch<T, W> nodeMatch;
	private final OneLevelMatcher<TT, TW> oneLevelMatcher;

	private final TreeModel<TT, T> nodeTreeModel;
	private final TreeModel<TW, W> wildcaldTreeModel;

	public MultiLevelMatcher(NodeMatch<T, W> nodeMatch, TreeModel<TT, T> nodeTreeModel,
			TreeModel<TW, W> wildcaldTreeModel) {
		this.nodeMatch = nodeMatch;
		this.nodeTreeModel = nodeTreeModel;
		this.wildcaldTreeModel = wildcaldTreeModel;

		NodeMatch<TT, TW> treeNodeMatch = new NodeMatch<TT, TW>() {
			@Override
			public boolean match(TW wildcard, TT node) {
				// match first the node itself
				boolean ok = MultiLevelMatcher.this.nodeMatch.match(
						MultiLevelMatcher.this.wildcaldTreeModel.getValue(wildcard),
						MultiLevelMatcher.this.nodeTreeModel.getValue(node));
				if (!ok) {
					return false;
				}
				if (MultiLevelMatcher.this.wildcaldTreeModel.getChildrenCount(wildcard) > 0) {
					// if the wildcard has children continue the matching to the children
					List<TW> unorderedWildcards = MultiLevelMatcher.this.wildcaldTreeModel.getChildren(wildcard, false);
					if (unorderedWildcards.size() > 0
							&& oneLevelMatcher.matchUnordered(
									MultiLevelMatcher.this.nodeTreeModel.getDescendants(node, false),
									unorderedWildcards).isEmpty()) {
						return false;
					}
					List<TW> orderedWildcards = MultiLevelMatcher.this.wildcaldTreeModel.getChildren(wildcard, true);
					if (orderedWildcards.size() > 0) {
						return !oneLevelMatcher.matchOrdered(
								MultiLevelMatcher.this.nodeTreeModel.getDescendants(node, true), orderedWildcards)
								.isEmpty();
					}
				}
				return true;
			}
		};
		oneLevelMatcher = new OneLevelMatcher<TT, TW>(treeNodeMatch);
	}

	public boolean match(TT parentNode, TW wildcardParent) {
		List<TT> initialNodes = Collections.singletonList(parentNode);
		List<TW> initialWildcards = Collections.singletonList(wildcardParent);
		List<List<TT>> match = oneLevelMatcher.matchOrdered(initialNodes, initialWildcards);
		return !match.isEmpty();
	}
}

package org.bugby.matcher.acr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;

import com.google.common.base.Supplier;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

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
	private final NodeMatch<TT, TW> treeNodeMatch;
	private final OneLevelMatcher<TT, TW> oneLevelMatcher;

	private final TreeModel<TT, T> nodeTreeModel;
	private final TreeModel<TW, W> wildcardTreeModel;

	private Multimap<TW, TT> currentMatch = Multimaps.newListMultimap(new IdentityHashMap<TW, Collection<TT>>(),
			new Supplier<List<TT>>() {
				@Override
				public List<TT> get() {
					return new ArrayList<TT>();
				}
			});

	public MultiLevelMatcher(NodeMatch<T, W> nodeMatch, TreeModel<TT, T> nodeTreeModel,
			TreeModel<TW, W> wildcaldTreeModel) {
		this.nodeMatch = nodeMatch;
		this.nodeTreeModel = nodeTreeModel;
		this.wildcardTreeModel = wildcaldTreeModel;

		treeNodeMatch = new NodeMatch<TT, TW>() {
			@Override
			public boolean match(TW wildcard, TT node) {
				// match first the node itself
				boolean ok = MultiLevelMatcher.this.nodeMatch.match(
						MultiLevelMatcher.this.wildcardTreeModel.getValue(wildcard),
						MultiLevelMatcher.this.nodeTreeModel.getValue(node));
				if (!ok) {
					return false;
				}
				if (MultiLevelMatcher.this.wildcardTreeModel.getChildrenCount(wildcard) > 0) {
					// if the wildcard has children continue the matching to the children
					List<TW> unorderedWildcards = MultiLevelMatcher.this.wildcardTreeModel.getChildren(wildcard, false);
					if (unorderedWildcards.size() > 0) {
						List<List<TT>> unorderedMatch = oneLevelMatcher.matchUnordered(
								MultiLevelMatcher.this.nodeTreeModel.getChildren(node, false), unorderedWildcards);

						if (unorderedMatch.isEmpty()) {
							return false;
						}
					}
					List<TW> orderedWildcards = MultiLevelMatcher.this.wildcardTreeModel.getChildren(wildcard, true);
					if (orderedWildcards.size() > 0) {
						List<List<TT>> orderedMatch = oneLevelMatcher.matchOrdered(
								MultiLevelMatcher.this.nodeTreeModel.getDescendants(node, true), orderedWildcards);

						return !orderedMatch.isEmpty();
					}
				}
				currentMatch.put(wildcard, node);
				return true;
			}

			@Override
			public MatchingType getMatchingType(TW wildcard) {
				return MultiLevelMatcher.this.nodeMatch.getMatchingType(MultiLevelMatcher.this.wildcardTreeModel
						.getValue(wildcard));
			}

			@Override
			public boolean isFirstChild(List<TT> nodes, int index) {
				return MultiLevelMatcher.this.nodeTreeModel.isFirstChild(nodes.get(index));
			}

			@Override
			public boolean isLastChild(List<TT> nodes, int index) {
				return MultiLevelMatcher.this.nodeTreeModel.isLastChild(nodes.get(index));
			}

			@Override
			public void removedNodeFromMatch(TT node) {
				MultiLevelMatcher.this.nodeMatch.removedNodeFromMatch(MultiLevelMatcher.this.nodeTreeModel
						.getValue(node));

			}
		};
		oneLevelMatcher = new OneLevelMatcher<TT, TW>(treeNodeMatch);
	}

	/**
	 * 
	 * @param parentNode
	 * @param wildcardParent
	 * @return the list with each node that matches the wildcard terminal (i.e. the last node - depth order - in the
	 *         wildcard tree)
	 */
	public Multimap<TW, TT> match(TT parentNode, TW wildcardParent) {
		currentMatch.clear();
		boolean fullMatch = treeNodeMatch.match(wildcardParent, parentNode);
		return fullMatch ? currentMatch : HashMultimap.<TW, TT>create();
	}

}

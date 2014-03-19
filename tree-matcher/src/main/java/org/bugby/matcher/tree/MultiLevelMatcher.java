package org.bugby.matcher.tree;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Supplier;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;

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

	private Multimap<TW, TT> currentMatch = Multimaps.newSetMultimap(new IdentityHashMap<TW, Collection<TT>>(), new Supplier<Set<TT>>() {
		@Override
		public Set<TT> get() {
			return Sets.newIdentityHashSet();
		}
	});

	public MultiLevelMatcher(NodeMatch<T, W> nodeMatch, TreeModel<TT, T> nodeTreeModel, TreeModel<TW, W> wildcaldTreeModel) {
		this.nodeMatch = nodeMatch;
		this.nodeTreeModel = nodeTreeModel;
		this.wildcardTreeModel = wildcaldTreeModel;

		treeNodeMatch = new NodeMatch<TT, TW>() {
			@Override
			public boolean match(TW wildcard, TT node) {
				// match first the node itself
				boolean ok = MultiLevelMatcher.this.nodeMatch.match(MultiLevelMatcher.this.wildcardTreeModel.getValue(wildcard),
						MultiLevelMatcher.this.nodeTreeModel.getValue(node));
				if (!ok) {
					return false;
				}
				if (MultiLevelMatcher.this.wildcardTreeModel.getChildrenCount(wildcard) > 0) {
					// if the wildcard has children continue the matching to the children
					if (!matchUnorderedChildren(wildcard, node)) {
						return false;
					}

					if (!matchOrderedChildren(wildcard, node)) {
						return false;
					}

				}
				if (!currentMatch.containsEntry(wildcard, node)) {
					currentMatch.put(wildcard, node);
				}
				return true;
			}

			@Override
			public MatchingType getMatchingType(TW wildcard) {
				return MultiLevelMatcher.this.nodeMatch.getMatchingType(MultiLevelMatcher.this.wildcardTreeModel.getValue(wildcard));
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
				MultiLevelMatcher.this.nodeMatch.removedNodeFromMatch(MultiLevelMatcher.this.nodeTreeModel.getValue(node));

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
		return fullMatch ? currentMatch : HashMultimap.<TW, TT> create();
	}

	private boolean matchUnorderedChildren(TW wildcard, TT node) {
		ListMultimap<String, TW> unorderedWildcards = MultiLevelMatcher.this.wildcardTreeModel.getChildren(wildcard, false);
		if (unorderedWildcards.size() > 0) {
			ListMultimap<String, TT> nodes = MultiLevelMatcher.this.nodeTreeModel.getChildren(node, false);

			for (Map.Entry<String, Collection<TW>> wildcardEntry : unorderedWildcards.asMap().entrySet()) {
				// match the children with the same name
				List<List<TT>> unorderedMatch = oneLevelMatcher.matchUnordered(nodes.get(wildcardEntry.getKey()),
						(List<TW>) wildcardEntry.getValue());

				if (unorderedMatch.isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean matchOrderedChildren(TW wildcard, TT node) {
		ListMultimap<String, TW> orderedWildcards = MultiLevelMatcher.this.wildcardTreeModel.getChildren(wildcard, true);
		if (orderedWildcards.size() > 0) {
			ListMultimap<String, TT> nodes = MultiLevelMatcher.this.nodeTreeModel.getDescendants(node, true);

			for (Map.Entry<String, Collection<TW>> wildcardEntry : orderedWildcards.asMap().entrySet()) {
				// match the children with the same name
				List<List<TT>> orderedMatch = oneLevelMatcher.matchOrdered(nodes.get(wildcardEntry.getKey()),
						(List<TW>) wildcardEntry.getValue());

				if (orderedMatch.isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}
}

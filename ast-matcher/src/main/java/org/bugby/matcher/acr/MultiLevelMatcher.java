package org.bugby.matcher.acr;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

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

	private TW terminalWildcard;
	private Set<T> currentResult;

	private Multimap<TW, TT> currentMatch = HashMultimap.create();

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
								MultiLevelMatcher.this.nodeTreeModel.getDescendants(node, false), unorderedWildcards);

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

	private List<T> saveLastPositions(List<List<TT>> match) {
		List<T> ret = new ArrayList<T>();
		for (List<TT> list : match) {
			if (!list.isEmpty()) {
				ret.add(nodeTreeModel.getValue(list.get(list.size() - 1)));
			}
		}
		return ret;
	}

	private TW findTerminal(TW wildcardParent) {
		TW terminal = null;
		List<TW> descendants = wildcardTreeModel.getDescendants(wildcardParent, true);
		if (!descendants.isEmpty()) {
			// last normal wildcard
			for (int i = descendants.size() - 1; i >= 0; --i) {
				terminal = descendants.get(i);
				if (treeNodeMatch.getMatchingType(terminal) == MatchingType.normal) {
					break;
				}
			}
			// TODO skip virtual nodes and begin/end
			System.out.println("TERMINAL is " + terminal);
		} else {
			descendants = wildcardTreeModel.getDescendants(wildcardParent, false);
			if (!descendants.isEmpty()) {
				// XXX not sure this is ok (when only unordered descendants exist)
				terminal = descendants.get(descendants.size() - 1);
			} else {
				terminal = wildcardParent;
			}
		}
		return terminal;
	}
}

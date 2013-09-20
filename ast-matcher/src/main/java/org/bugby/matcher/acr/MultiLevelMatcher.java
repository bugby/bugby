package org.bugby.matcher.acr;

import java.util.ArrayList;
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
	private final NodeMatch<TT, TW> treeNodeMatch;
	private final OneLevelMatcher<TT, TW> oneLevelMatcher;

	private final TreeModel<TT, T> nodeTreeModel;
	private final TreeModel<TW, W> wildcaldTreeModel;

	private TW terminalWildcard;
	private List<T> currentResult;

	public MultiLevelMatcher(NodeMatch<T, W> nodeMatch, TreeModel<TT, T> nodeTreeModel,
			TreeModel<TW, W> wildcaldTreeModel) {
		this.nodeMatch = nodeMatch;
		this.nodeTreeModel = nodeTreeModel;
		this.wildcaldTreeModel = wildcaldTreeModel;

		treeNodeMatch = new NodeMatch<TT, TW>() {
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
				} else {
					if (wildcard == terminalWildcard) {
						// found a terminal result
						currentResult.add(MultiLevelMatcher.this.nodeTreeModel.getValue(node));
					}
				}
				return true;
			}

			@Override
			public MatchingType getMatchingType(TW wildcard) {
				return MultiLevelMatcher.this.nodeMatch.getMatchingType(MultiLevelMatcher.this.wildcaldTreeModel
						.getValue(wildcard));
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
	public List<T> match(TT parentNode, TW wildcardParent) {
		currentResult = new ArrayList<T>();
		terminalWildcard = findTerminal(wildcardParent);
		treeNodeMatch.match(wildcardParent, parentNode);
		return currentResult;
	}

	private TW findTerminal(TW wildcardParent) {
		TW terminal = null;
		List<TW> descendants = wildcaldTreeModel.getDescendants(wildcardParent, true);
		if (!descendants.isEmpty()) {
			terminal = descendants.get(descendants.size() - 1);
		} else {
			descendants = wildcaldTreeModel.getDescendants(wildcardParent, false);
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

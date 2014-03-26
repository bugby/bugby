package org.bugby.matcher.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class OneLevelMatcher<T, W> {
	private final NodeMatch<T, W> nodeMatch;

	private class MatchPosition {
		private final int node;
		private final int wildcard;

		public MatchPosition(int node, int wildcard) {
			this.node = node;
			this.wildcard = wildcard;
		}

		public int getNode() {
			return node;
		}

		public int getWildcard() {
			return wildcard;
		}

	}

	public OneLevelMatcher(NodeMatch<T, W> nodeMatch) {
		this.nodeMatch = nodeMatch;
	}

	/**
	 * the wildcards have to be matched in order
	 * 
	 * @param nodes
	 * @param wildcards
	 * @return
	 */
	public List<List<T>> matchOrdered(List<T> nodes, List<W> wildcards) {
		Stack<MatchPosition> matchingStack = new Stack<MatchPosition>();
		List<List<T>> matchings = new ArrayList<List<T>>();
		// special case - EMPTY
		if (wildcards.size() == 1 && nodeMatch.getMatchingType(wildcards.get(0)) == MatchingType.empty) {
			// assert w == 0 && w == wildcards.size() - 1
			if (nodes.isEmpty()) {
				matchings.add(new ArrayList<T>());
			}
			return matchings;
		}

		matchingStack.push(new MatchPosition(-1, 0));

		while (!matchingStack.isEmpty()) {
			// look for a next start
			MatchPosition start = matchingStack.pop();
			// notice that the node is no longer part of the ongoing match, so context can be cleaned
			if (start.node >= 0) {
				nodeMatch.removedNodeFromMatch(nodes.get(start.node));
			}
			if (start.getNode() < nodes.size() - 1) {
				if (matchOrderedOne(nodes, wildcards, start.getNode() + 1, start.getWildcard(), matchingStack)) {
					// dump the stack in a result
					List<T> oneMatch = new ArrayList<T>();
					for (MatchPosition pos : matchingStack) {
						oneMatch.add(nodes.get(pos.getNode()));
					}
					matchings.add(oneMatch);
				}
			}
		}
		return matchings;
	}

	private boolean matchOrderedOne(List<T> nodes, List<W> wildcards, int startN, int startW, Stack<MatchPosition> matchingStack) {
		int n = startN, w = startW;
		List<W> constraints = new ArrayList<W>();

		w = advanceWildcardIndex(wildcards, startW - 1, constraints);

		while (n < nodes.size() && w < wildcards.size()) {
			W wildcard = wildcards.get(w);

			// !!!XXX the order of conditions check affects how the terminals are chosen in MultiLevelMatcher
			if (validateConstraints(constraints, nodes, n) && nodeMatch.match(wildcard, nodes.get(n))) {
				matchingStack.push(new MatchPosition(n, w));
				w = advanceWildcardIndex(wildcards, w, constraints);
			}
			n++;
		}
		return w == wildcards.size();
	}

	private int advanceWildcardIndex(List<W> wildcards, int currentW, List<W> constraints) {
		int w = currentW + 1;
		constraints.clear();
		// pre-constraints
		while (w < wildcards.size()) {
			W wildcard = wildcards.get(w);
			if (nodeMatch.getMatchingType(wildcard) != MatchingType.normal) {
				constraints.add(wildcard);
			} else {
				break;
			}
			w++;
		}

		// post constraints - look ahead
		for (int i = w + 1; i < wildcards.size(); ++i) {
			W wildcard = wildcards.get(i);
			if (nodeMatch.getMatchingType(wildcard) != MatchingType.normal) {
				constraints.add(wildcard);
			} else {
				break;
			}
		}

		return w;
	}

	private boolean validateConstraints(List<W> constraints, List<T> nodes, int n) {
		for (W constraint : constraints) {
			if (!nodeMatch.match(constraint, nodes.get(n))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * the wildcards may match in any order
	 * 
	 * @param nodes
	 * @param wildcards
	 * @return
	 */
	public List<List<T>> matchUnordered(List<T> nodes, List<W> wildcards) {
		if (nodes.isEmpty() || wildcards.isEmpty()) {
			return Collections.emptyList();
		}
		if (nodes.size() < wildcards.size()) {
			return Collections.emptyList();
		}

		List<List<T>> result = new ArrayList<List<T>>();
		// the solution is the match for the first wildcard plus the match of the rest wildcards
		W firstWildcard = wildcards.get(0);
		List<W> nextWildcards = wildcards.subList(1, wildcards.size());

		for (int n = 0; n < nodes.size(); ++n) {
			if (nodeMatch.match(firstWildcard, nodes.get(n))) {
				if (wildcards.size() == 1) {
					// single result
					result.add(Collections.singletonList(nodes.get(n)));
				} else {
					// add the current result to each of the results when matching the remaining nodes and wildcards
					List<T> nodesWithoutCurrent = listWithout(nodes, n);

					List<List<T>> subresult = matchUnordered(nodesWithoutCurrent, nextWildcards);
					for (List<T> s : subresult) {
						List<T> oneResult = new ArrayList<T>(s.size() + 1);
						oneResult.add(nodes.get(n));
						oneResult.addAll(s);
						result.add(oneResult);
					}
				}
				nodeMatch.removedNodeFromMatch(nodes.get(n));
			}
		}
		return result;
	}

	private <V> List<V> listWithout(List<V> in, int index) {
		List<V> ret = new ArrayList<V>(in);
		ret.remove(index);
		return ret;

	}
}

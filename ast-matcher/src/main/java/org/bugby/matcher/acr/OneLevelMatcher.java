package org.bugby.matcher.acr;

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
	 * the wildcards have to be match in order
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

	private boolean matchOrderedOne(List<T> nodes, List<W> wildcards, int startN, int startW,
			Stack<MatchPosition> matchingStack) {
		int n = startN, w = startW;
		boolean nextMustMatch = false;
		while (n < nodes.size() && w < wildcards.size()) {
			W wildcard = wildcards.get(w);

			if (nodeMatch.getMatchingType(wildcard) == MatchingType.begin) {
				// assert w == 0
				nextMustMatch = true;
				w++;
				continue;
			}
			// check END match - look ahead
			if (w == wildcards.size() - 2 && nodeMatch.getMatchingType(wildcards.get(w + 1)) == MatchingType.end) {
				matchingStack.push(new MatchPosition(nodes.size() - 1, w));
				return nodeMatch.match(wildcard, nodes.get(nodes.size() - 1));
			}
			if (nodeMatch.match(wildcard, nodes.get(n))) {
				matchingStack.push(new MatchPosition(n, w));
				w++;
				nextMustMatch = false;
			} else if (nextMustMatch) {
				// a match was expected here
				return false;
			}
			n++;
		}
		return (w == wildcards.size());
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
		List<List<T>> result = new ArrayList<List<T>>();
		for (int i = 0; i < nodes.size(); ++i) {
			for (int j = 0; j < wildcards.size(); ++j) {
				if (nodeMatch.match(wildcards.get(j), nodes.get(i))) {
					if (wildcards.size() == 1) {
						// single result
						result.add(Collections.singletonList(nodes.get(i)));
					} else {
						// add the current result to each of the results when matching the remaining nodes and wildcards
						List<T> nodesWithoutCurrent = listWithout(nodes, i);
						List<W> nextWildcards = wildcards.subList(j + 1, wildcards.size());
						List<List<T>> subresult = matchUnordered(nodesWithoutCurrent, nextWildcards);
						for (List<T> s : subresult) {
							List<T> oneResult = new ArrayList<T>(s.size() + 1);
							oneResult.add(nodes.get(i));
							oneResult.addAll(s);
							result.add(oneResult);
						}
					}
				}
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

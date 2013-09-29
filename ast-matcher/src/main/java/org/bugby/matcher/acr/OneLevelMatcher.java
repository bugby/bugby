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

	private boolean matchOrderedOne(List<T> nodes, List<W> wildcards, int startN, int startW,
			Stack<MatchPosition> matchingStack) {
		int n = startN, w = startW;
		List<Constraint<T, W>> constraints = new ArrayList<Constraint<T, W>>();

		w = advanceWildcardIndex(wildcards, startW - 1, constraints);

		while (n < nodes.size() && w < wildcards.size()) {
			W wildcard = wildcards.get(w);

			// !!!XXX the order of conditions check affects how the terminals are chosen in MultiLevelMatcher
			if (validateConstraints(constraints, wildcard, nodes, n) && nodeMatch.match(wildcard, nodes.get(n))) {
				matchingStack.push(new MatchPosition(n, w));
				w = advanceWildcardIndex(wildcards, w, constraints);
			}
			n++;
		}
		return (w == wildcards.size());
	}

	private int advanceWildcardIndex(List<W> wildcards, int currentW, List<Constraint<T, W>> constraints) {
		int w = currentW + 1;
		constraints.clear();
		// pre-constraints
		while (w < wildcards.size()) {
			W wildcard = wildcards.get(w);
			if (nodeMatch.getMatchingType(wildcard) != MatchingType.normal) {
				constraints.add(constraintOf(nodeMatch.getMatchingType(wildcard), true));
			} else {
				break;
			}
			w++;
		}

		// post constraints - look ahead
		for (int i = w + 1; i < wildcards.size(); ++i) {
			W wildcard = wildcards.get(i);
			if (nodeMatch.getMatchingType(wildcard) != MatchingType.normal) {
				constraints.add(constraintOf(nodeMatch.getMatchingType(wildcard), false));
			} else {
				break;
			}
		}

		return w;
	}

	private boolean validateConstraints(List<Constraint<T, W>> constraints, W wildcard, List<T> nodes, int n) {
		for (Constraint<T, W> constraint : constraints) {
			if (!constraint.validate(wildcard, nodes, n)) {
				return false;
			}
		}
		return true;
	}

	private interface Constraint<T, W> {
		public boolean validate(W w, List<T> nodes, int nodeIndex);
	}

	private class BeginConstraint implements Constraint<T, W> {
		@Override
		public boolean validate(W w, List<T> nodes, int nodeIndex) {
			return nodeMatch.isFirstChild(nodes, nodeIndex);
		}
	}

	private class EndConstraint implements Constraint<T, W> {
		@Override
		public boolean validate(W w, List<T> nodes, int nodeIndex) {
			return nodeMatch.isLastChild(nodes, nodeIndex);
		}
	}

	private Constraint<T, W> constraintOf(MatchingType type, boolean before) {
		switch (type) {
		case begin:
			return new BeginConstraint();
		case end:
			return new EndConstraint();
		default:
			return null;
		}
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
					// TODO not 100% sure that is here i should put this
					nodeMatch.removedNodeFromMatch(nodes.get(i));
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

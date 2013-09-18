package org.bugby.matcher.acr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class OneLevelMatcher {
	/**
	 * means next node must match
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final Wildcard<?> BEGIN = new DefaultWildcard(null);
	/**
	 * means the last match should've been on the last position
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final Wildcard<?> END = new DefaultWildcard(null);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final Wildcard<?> EMPTY = new DefaultWildcard(null);

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

	/**
	 * the wildcards have to be match in order
	 * 
	 * @param nodes
	 * @param wildcards
	 * @return
	 */
	public <T> List<List<T>> matchOrdered(List<T> nodes, List<? extends Wildcard<T>> wildcards) {
		Stack<MatchPosition> matchingStack = new Stack<OneLevelMatcher.MatchPosition>();
		List<List<T>> matchings = new ArrayList<List<T>>();
		// special case - EMPTY
		if (wildcards.size() == 1 && wildcards.get(0) == EMPTY) {
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

	private <T> boolean matchOrderedOne(List<T> nodes, List<? extends Wildcard<T>> wildcards, int startN, int startW,
			Stack<MatchPosition> matchingStack) {
		int n = startN, w = startW;
		boolean nextMustMatch = false;
		while (n < nodes.size() && w < wildcards.size()) {
			Wildcard<T> wildcard = wildcards.get(w);

			if (wildcard == BEGIN) {
				// assert w == 0
				nextMustMatch = true;
				w++;
				continue;
			}
			// check END match - look ahead
			if (w == wildcards.size() - 2 && wildcards.get(w + 1) == OneLevelMatcher.END) {
				matchingStack.push(new MatchPosition(nodes.size() - 1, w));
				return wildcard.match(nodes.get(nodes.size() - 1));
			}
			if (wildcard.match(nodes.get(n))) {
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
	public <T> boolean matchUnordered(List<T> nodes, List<? extends Wildcard<T>> wildcards) {
		List<? extends Wildcard<T>> copyWildcards = new ArrayList<Wildcard<T>>(wildcards);
		for (T node : nodes) {
			Iterator<? extends Wildcard<T>> it = copyWildcards.iterator();
			while (it.hasNext()) {
				if (it.next().match(node)) {
					it.remove();
					break;
				}
			}
		}

		return copyWildcards.isEmpty();
	}

	public static void main(String[] args) {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<String> nodes = Arrays.asList("a", "b", "c");
		List<? extends Wildcard<String>> wildcards = Arrays.asList(new DefaultWildcard<String>("x"));
		System.out.println(matcher.matchOrdered(nodes, wildcards));
	}
}

package org.bugby.matcher.acr;

import java.util.Arrays;
import java.util.List;

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

	public <T> boolean match(List<T> nodes, List<? extends Wildcard<T>> wildcards) {
		int n = 0, w = 0;
		int lastMatch = -1;
		boolean nextMustMatch = false;
		while (n < nodes.size() && w < wildcards.size()) {
			Wildcard<T> wildcard = wildcards.get(w);
			if (wildcard == BEGIN) {
				// assert w == 0
				nextMustMatch = true;
				w++;
				continue;
			}
			if (wildcard == END) {
				// assert w == wildcards.size() - 1
				break;
			}
			if (wildcard.match(nodes.get(n))) {
				w++;
				lastMatch = n;
				nextMustMatch = false;
			} else if (nextMustMatch) {
				// a match was expected here
				return false;
			}
			n++;
		}
		if (w == wildcards.size()) {
			// full normal match
			return true;
		}
		if (w == wildcards.size() - 1 && wildcards.get(w) == OneLevelMatcher.END && lastMatch == nodes.size() - 1) {
			// match on end
			return true;
		}
		return false;
	}

	public static interface Wildcard<T> {
		public boolean match(T t);
	}

	public static class DefaultWildcard<T> implements Wildcard<T> {
		private final T value;

		public DefaultWildcard(T value) {
			this.value = value;
		}

		@Override
		public boolean match(T t) {
			return t.equals(value);
		}
	}

	public static void main(String[] args) {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<String> nodes = Arrays.asList("a", "b", "c");
		List<? extends Wildcard<String>> wildcards = Arrays.asList(new DefaultWildcard<String>("x"));
		System.out.println(matcher.match(nodes, wildcards));
	}
}

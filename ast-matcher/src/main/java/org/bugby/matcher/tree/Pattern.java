package org.bugby.matcher.tree;

import org.bugby.matcher.value.ValueMatcher;

/**
 * Pattern defining matching criteria.
 * 
 * This is intended to be a value in a second Tree (i.e. Tree&lt;Pattern&lt;T&gt;&gt;) which will be used
 * for matching against an actual Tree&lt;T&gt;.
 * 
 * @author catac
 */
public class Pattern<T> {

	// the matcher to use when first seeing a tree node (pre-order matcher)
	private final ValueMatcher<T> preMatcher;

	// optional matcher to use after child-patterns were executed (post-order matcher)
	private final ValueMatcher<T> pstMatcher;

	// optional example for the pattern. This could have been part of the valueMatcher,
	// but passing it to the pattern allows having state-less ValueMatchers.
	private final T exampleValue;

	// optional reference pattern. This allows referring later a previously matched pattern
	// regexp: reference to a previously captured element
	private final Pattern<T> refPattern;

	// optional requirement that this pattern matches immediately after last matched pattern (or first match)
	// regexp: ^ (must match precisely here, not later)
	private final boolean hasNothingBefore;

	// optional requirement that this pattern matches immediately before next matched pattern (or last match)
	// regexp: $ (it's a leaf node)
	private final boolean hasNothingAfter;

	// running values - the actual tree node that was matched, or null if no match
	// regexp: captured element
	private Tree<T> matchedTree;

	public Pattern(Builder<T> builder) {
		this.preMatcher = builder.preMatcher;
		this.pstMatcher = builder.pstMatcher;
		this.exampleValue = builder.exampleValue;
		this.refPattern = builder.refPattern;
		this.hasNothingBefore = builder.hasNothingBefore;
		this.hasNothingAfter = builder.hasNothingAfter;
	}

	public static <T> Builder<T> pattern(ValueMatcher<T> valueMatcher) {
		return new Builder<T>(valueMatcher);
	}

	public T getExampleValue() {
		return exampleValue;
	}

	public static class Builder<T> {
		private final ValueMatcher<T> preMatcher;
		private ValueMatcher<T> pstMatcher;
		private T exampleValue;
		private Pattern<T> refPattern;
		private boolean hasNothingBefore;
		private boolean hasNothingAfter;

		public Builder(ValueMatcher<T> preMatcher) {
			this.preMatcher = preMatcher;
		}

		public Builder<T> withPostorderMatcher(ValueMatcher<T> aPstMatcher) {
			this.pstMatcher = aPstMatcher;
			return this;
		}

		public Builder<T> withExampleValue(T anExampleValue) {
			this.exampleValue = anExampleValue;
			return this;
		}

		public Builder<T> withRefPattern(Pattern<T> aRefPattern) {
			this.refPattern = aRefPattern;
			return this;
		}

		public Builder<T> withNothingBefore() {
			this.hasNothingBefore = true;
			return this;
		}

		public Builder<T> withNothingAfter() {
			this.hasNothingAfter = true;
			return this;
		}

		public Pattern<T> build() {
			return new Pattern<T>(this);
		}
	}
}

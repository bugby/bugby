package org.bugby.matcher.tree;

public class Pattern<T> {

	public enum Flags {
		// matching flags
		MATCHES_ANYTHING, // regexp: .
		MATCHES_ANY_OF, // regexp: [abc]
		MATCHES_ALL_EXCEPT, // regexp: [^abc]

		// positional flags
		HAS_NOTHING_BEFORE, // regexp: ^ (must match precisely here, not later)
		HAS_NOTHING_AFTER, // regexp: $ (it's a leaf node)

		// repeating flags. TODO: do we really need these?
		REPEATS_ONCE_OR_NOT_AT_ALL, // regexp: X?
		REPEATS_ZERO_OR_MORE_TIMES, // regexp: X*
		REPEATS_ONCE_OR_MORE_TIMES, // regexp: X+
		REPEATS_BOUNDED_TIMES, // regexp: X{n,m} (repeats between n and m times)

		// capturing flags
		IS_CAPTURING_GROUP, // regexp: (X)
		REFERS_CAPTURED_GROUP, // regexp: \n
	}
}

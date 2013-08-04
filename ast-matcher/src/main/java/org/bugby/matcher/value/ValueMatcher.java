package org.bugby.matcher.value;

import org.bugby.matcher.tree.Pattern;

public interface ValueMatcher<T> {
	boolean matches(Pattern<T> pattern, T value);
}

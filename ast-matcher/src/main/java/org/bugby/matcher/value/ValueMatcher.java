package org.bugby.matcher.value;

public interface ValueMatcher<T> {
	boolean matches(T actualValue, T patternValue);
}

package org.bugby.pattern.api.matcher.content;

public class AnyMatcher<T> implements ContentMatcher<T> {

	@Override
	public boolean matches(T input) {
		return true;
	}
}

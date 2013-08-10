package org.bugby.pattern.api.matcher.content;

public interface ContentMatcher<T> {

	boolean matches(T input);
}

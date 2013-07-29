package org.bugby.pattern.api.matcher;

public interface Matcher<T> {

	boolean matches(T input);
}

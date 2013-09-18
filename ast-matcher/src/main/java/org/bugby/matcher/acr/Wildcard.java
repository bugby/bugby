package org.bugby.matcher.acr;

public interface Wildcard<T> {
	public boolean match(T t);
}
package org.bugby.matcher.acr;

interface Wildcard<T> {
	public boolean match(T t);

	public MatchingType getMatchingType();
}
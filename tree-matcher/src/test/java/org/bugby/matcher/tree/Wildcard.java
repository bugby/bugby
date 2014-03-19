package org.bugby.matcher.tree;

import org.bugby.matcher.tree.MatchingType;

interface Wildcard<T> {
	public boolean match(T t);

	public MatchingType getMatchingType();
}
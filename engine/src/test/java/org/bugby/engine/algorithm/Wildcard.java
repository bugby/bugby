package org.bugby.engine.algorithm;

import org.bugby.api.wildcard.MatchingType;

interface Wildcard<T> {
	public boolean match(T t);

	public MatchingType getMatchingType();
}
package org.bugby.engine.algorithm;

import java.util.List;

import org.bugby.api.wildcard.MatchingType;

interface Wildcard<T> {
	public boolean match(T t, List<Wildcard<T>> wildcards, List<T> nodes);

	public MatchingType getMatchingType();
}
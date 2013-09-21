package org.bugby.matcher.acr;

import java.util.List;

public interface NodeMatch<T, W> {
	public boolean match(W wildcard, T node);

	public MatchingType getMatchingType(W wildcard);

	public boolean isFirstChild(List<T> nodes, int index);

	public boolean isLastChild(List<T> nodes, int index);
}

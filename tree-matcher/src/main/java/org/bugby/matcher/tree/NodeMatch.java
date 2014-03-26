package org.bugby.matcher.tree;


public interface NodeMatch<T, W> {
	public boolean match(W wildcard, T node);

	public MatchingType getMatchingType(W wildcard);

	public void removedNodeFromMatch(T node);

}

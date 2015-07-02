package org.bugby.matcher.algorithm;

import java.util.List;

import org.bugby.api.MatchingType;

public interface NodeMatch<T, W, R> {
	/**
	 * @param wildcard
	 * @param node
	 * @param wildcards - the current list of the wildcards to be matched
	 * @param nodes - the current list of nodes to be matched
	 * @return true if the given nodes matches the given wildcard
	 */
	public boolean match(W wildcard, T node, List<W> wildcards, List<T> nodes);

	public MatchingType getMatchingType(W wildcard);

	public void removedNodeFromMatch(W wildcard, T node);

	public R buildResult(W wildcard, T node);

	/**
	 * called when a full solution was found during the current multi-node matching
	 */
	public void solutionFound();

}

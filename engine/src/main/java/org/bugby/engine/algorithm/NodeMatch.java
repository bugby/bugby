package org.bugby.engine.algorithm;

import org.bugby.api.wildcard.MatchingType;

public interface NodeMatch<T, W, R> {
	public boolean match(W wildcard, T node);

	public MatchingType getMatchingType(W wildcard);

	public void removedNodeFromMatch(W wildcard, T node);

	public R buildResult(W wildcard, T node);

	/**
	 * called when a full solution was found during the current multi-node matching
	 */
	public void solutionFound();

}

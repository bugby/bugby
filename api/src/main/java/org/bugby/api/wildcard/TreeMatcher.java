package org.bugby.api.wildcard;

import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;

public interface TreeMatcher {
	public MatchingType getMatchingType();

	/**
	 * called before starting the matching, multiple siblings with multiple nodes from the AST to be checked. Called once for each of the
	 * siblings involved in the matching process.
	 * @param ordered
	 */
	public void startMatching(boolean ordered, MatchingContext context);

	public boolean matches(Tree node, MatchingContext context);

	/**
	 * called once the matching was returned by the multiple matchers. The matcher has a chance to alter the final result returned to the caller
	 * @param currentResult
	 * @return
	 */
	public Multimap<TreeMatcher, Tree> endMatching(Multimap<TreeMatcher, Tree> currentResult, MatchingContext context);

}

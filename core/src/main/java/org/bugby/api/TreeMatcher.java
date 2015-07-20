package org.bugby.api;

import java.util.List;

import com.sun.source.tree.Tree;

public interface TreeMatcher {
	public int getId();

	public MatchingType getMatchingType();

	public Tree getPatternNode();

	/**
	 * called before starting the matching, multiple siblings with multiple nodes from the AST to be checked. Called once for each of the
	 * siblings involved in the matching process.
	 * @param ordered
	 */
	public TreeMatcherExecutionType startMatching(boolean ordered, MatchingContext context);

	/**
	 * called only for the matchers that were included in the matching process.
	 * @param node
	 * @param context
	 * @return
	 */
	public boolean matches(Tree node, MatchingContext context);

	/**
	 * called once the matching was returned by the multiple matchers. The matcher has a chance to alter the final result returned to the caller
	 * @param currentResult
	 * @return
	 */
	public List<List<MatchingPath>> endMatching(List<List<MatchingPath>> currentResult, MatchingContext context);

}

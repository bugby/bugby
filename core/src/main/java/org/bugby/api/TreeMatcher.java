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
	 * it can be called multiple times during the matches process, when the sourcenode is no longer part of the current solution. Imagine you
	 * have to match the pattern A,B and the nodes to be matched are a1,b1,b2. A first solution a1, b1 will be found. Before find the solution
	 * a1, b2, the remove is called with b1.
	 *
	 * @param context
	 */
	public void removeFromMatch(Tree node, MatchingContext context);

	/**
	 * called once the matching was returned by the multiple matchers. The matcher has a chance to alter the final result returned to the caller
	 * @param currentResult
	 * @return
	 */
	public List<List<MatchingPath>> endMatching(List<List<MatchingPath>> currentResult, MatchingContext context);

}

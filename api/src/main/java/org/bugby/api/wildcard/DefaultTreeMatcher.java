package org.bugby.api.wildcard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Multimap;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.Tree;

abstract public class DefaultTreeMatcher implements TreeMatcher {

	protected FluidMatcher matching(Tree node, MatchingContext context) {
		return new FluidMatcher(context, node, this);
	}

	public static List<TreeMatcher> build(TreeMatcherFactory factory, List<? extends Tree> nodes) {
		if (nodes.isEmpty()) {
			return Collections.emptyList();
		}
		List<TreeMatcher> matchers = new ArrayList<TreeMatcher>(nodes.size());
		for (Tree node : nodes) {
			TreeMatcher matcher = factory.build(node);
			if (matcher != null) {
				matchers.add(matcher);
			}
		}
		return matchers;
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.normal;
	}

	@SuppressWarnings("unchecked")
	protected <T extends ExpressionTree> T removeExpressionStatement(Tree node) {
		if (node instanceof ExpressionStatementTree) {
			return (T) ((ExpressionStatementTree) node).getExpression();
		}

		if (node instanceof ExpressionTree) {
			return (T) node;
		}
		return null;
	}

	// private final long id = ids.incrementAndGet();
	// private final Map<String, MethodCallExpr> patternAnnotations;
	//
	// public DefaultNodeMatcher(Node targetNode, Map<String, MethodCallExpr> patternAnnotations) {
	// this.targetNode = targetNode;
	// this.patternAnnotations = patternAnnotations;
	// }
	//
	// @Override
	// public boolean matches(TreeModel<Node, Node> treeModel, Node node, MatchingContext context) {
	// if (!node.getClass().equals(targetNode.getClass())) {
	// return false;
	// }
	// if (patternAnnotations.containsKey("$last") && !treeModel.isLastChild(node)) {
	// return false;
	// }
	//
	// return ASTModelBridges.getBridge(targetNode).areSimilar(targetNode, node);
	// }

	/**
	 * called before starting the matching, multiple siblings with multiple nodes from the AST to be checked. Called once for each of the
	 * siblings involved in the matching process.
	 * @param ordered
	 */
	@Override
	public void startMatching(boolean ordered, MatchingContext context) {
		//does nothing
	}

	/**
	 * called once the matching was returned by the multiple matchers. The matcher has a chance to alter the final result returned to the caller
	 * @param currentResult
	 * @return
	 */
	@Override
	public Multimap<TreeMatcher, Tree> endMatching(Multimap<TreeMatcher, Tree> currentResult, MatchingContext context) {
		//does nothing
		return currentResult;
	}
}

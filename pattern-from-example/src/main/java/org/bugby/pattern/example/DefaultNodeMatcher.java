package org.bugby.pattern.example;

import japa.parser.ast.Node;
import japa.parser.ast.expr.MethodCallExpr;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.matcher.acr.TreeModel;
import org.bugby.wildcard.api.MatchingContext;
import org.bugby.wildcard.api.WildcardNodeMatcher;

public class DefaultNodeMatcher implements WildcardNodeMatcher {
	private static AtomicLong ids = new AtomicLong(0);
	private final Node targetNode;
	private final long id = ids.incrementAndGet();
	private final Map<String, MethodCallExpr> patternAnnotations;

	public DefaultNodeMatcher(Node targetNode, Map<String, MethodCallExpr> patternAnnotations) {
		this.targetNode = targetNode;
		this.patternAnnotations = patternAnnotations;
	}

	@Override
	public boolean matches(TreeModel<Node, Node> treeModel, Node node, MatchingContext context) {
		if (!node.getClass().equals(targetNode.getClass())) {
			return false;
		}
		if (patternAnnotations.containsKey("$last") && !treeModel.isLastChild(node)) {
			return false;
		}

		return ASTModelBridges.getBridge(targetNode).areSimilar(targetNode, node);
	}

	public Node getTargetNode() {
		return targetNode;
	}

	@Override
	public String toString() {
		if (targetNode instanceof VirtualNode) {
			return "DefaultNodeMatcher [targetNode=Virtual:" + ((VirtualNode) targetNode).getName() + "@" + id + "]";
		}
		return "DefaultNodeMatcher [targetNode=" + targetNode.getClass() + "@" + id + "]";
	}

	@Override
	public boolean isOrdered() {
		return ASTModelBridges.getBridge(targetNode).isOrdered(targetNode);
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.normal;
	}

}

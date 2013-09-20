package org.bugby.pattern.example;

import japa.parser.ast.Node;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.wildcard.api.WildcardNodeMatcher;

public class DefaultNodeMatcher implements WildcardNodeMatcher {
	private final Node targetNode;

	public DefaultNodeMatcher(Node targetNode) {
		this.targetNode = targetNode;
	}

	@Override
	public boolean matches(Node node) {
		// TOOD here is VEEERY complicated. because it's not the simple recursive equals. i.e.
		// if statements should match, if they both have else
		// method call if they refer to the same method, with the same parameters !?
		// etc
		return node.getClass().equals(targetNode.getClass());
	}

	public Node getTargetNode() {
		return targetNode;
	}

	@Override
	public String toString() {
		return "DefaultNodeMatcher [targetNode=" + targetNode.getClass() + "]";
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

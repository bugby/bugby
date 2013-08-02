package org.bugby.wildcard.api;

import japa.parser.ast.Node;

public class DefaultNodeMatcher implements WildcardNodeMatcher<Node> {
	private final Node targetNode;

	public DefaultNodeMatcher(Node targetNode) {
		this.targetNode = targetNode;
	}

	@Override
	public boolean matches(Node node) {
		//TOOD here is VEEERY complicated. because it's not the simple recursive equals. i.e.
		// if statements should match, if they both have else
		//method call if they refer to the same method, with the same parameters !?
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

}

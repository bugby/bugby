package org.bugby.pattern.example;

import japa.parser.ast.Node;

import java.util.concurrent.atomic.AtomicLong;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.wildcard.api.WildcardNodeMatcher;

public class DefaultNodeMatcher implements WildcardNodeMatcher {
	private static AtomicLong ids = new AtomicLong(0);
	private final Node targetNode;
	private final long id = ids.incrementAndGet();

	public DefaultNodeMatcher(Node targetNode) {
		this.targetNode = targetNode;
	}

	@Override
	public boolean matches(Node node) {
		// TOOD here is VEEERY complicated. because it's not the simple recursive equals. i.e.
		// if statements should match, if they both have else
		// method call if they refer to the same method, with the same parameters !?
		// etc
		if (!node.getClass().equals(targetNode.getClass())) {
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

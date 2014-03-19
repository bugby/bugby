package org.bugby.engine;

import japa.parser.ast.Node;

import java.util.ArrayList;
import java.util.List;

import org.bugby.matcher.tree.TreeModel;
import org.richast.node.ASTNodeData;

public class ASTTreeModel implements TreeModel<Node, Node> {

	@Override
	public int getChildrenCount(Node parent) {
		// TODO optimize here
		return ASTModelBridges.getBridge(parent).getChildren(parent).size();
	}

	@Override
	public List<Node> getChildren(Node parent, boolean ordered) {
		// TODO optimize here
		List<Node> all = ASTModelBridges.getBridge(parent).getChildren(parent);
		List<Node> filtered = new ArrayList<Node>();
		for (Node node : all) {
			if (ASTModelBridges.getBridge(node).isOrdered(node) == ordered) {
				filtered.add(node);
			}
		}
		return filtered;
	}

	@Override
	public List<Node> getDescendants(Node parent, boolean ordered) {
		List<Node> descendants = new ArrayList<Node>();
		for (Node child : ASTModelBridges.getBridge(parent).getChildren(parent)) {
			if (isOrdered(child) == ordered) {
				descendants.add(child);
			}
			descendants.addAll(getDescendants(child, ordered));
		}
		return descendants;
	}

	@Override
	public Node getValue(Node node) {
		return node;
	}

	@Override
	public boolean isOrdered(Node node) {
		// TODO optimize here
		return ASTModelBridges.getBridge(node).isOrdered(node);
	}

	@Override
	public boolean isFirstChild(Node node) {
		// TODO optimize here
		Node parent = ASTNodeData.parent(node);
		List<Node> children = getChildren(parent, true);
		return children.get(0) == node;
	}

	@Override
	public boolean isLastChild(Node node) {
		// TODO optimize here
		Node parent = ASTNodeData.parent(node);
		List<Node> children = getChildren(parent, true);
		return children.get(children.size() - 1) == node;
	}

}

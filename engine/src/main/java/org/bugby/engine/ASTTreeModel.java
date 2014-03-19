package org.bugby.engine;

import japa.parser.ast.Node;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bugby.engine.model.TreeNodeDescriber;
import org.bugby.matcher.tree.TreeModel;
import org.richast.node.ASTNodeData;

import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

public class ASTTreeModel implements TreeModel<Node, Node> {
	private TreeNodeDescriber treeNodeDescriber = new TreeNodeDescriber();

	@Override
	public int getChildrenCount(Node parent) {
		// TODO optimize here
		return ASTModelBridges.getBridge(parent).getChildren(parent).size();
	}

	@Override
	public ListMultimap<String, Node> getChildren(final Node parent, final boolean ordered) {
		// TODO optimize here
		ListMultimap<String, Node> children = treeNodeDescriber.describeNode(parent);

		return ArrayListMultimap.create(Multimaps.filterKeys(children, new Predicate<String>() {
			@Override
			public boolean apply(String childType) {
				return isOrdered(parent, childType) == ordered;
			}
		}));
	}

	@Override
	public ListMultimap<String, Node> getDescendants(Node parent, boolean ordered) {
		ListMultimap<String, Node> children = treeNodeDescriber.describeNode(parent);
		ListMultimap<String, Node> descendants = ArrayListMultimap.create();

		for (Map.Entry<String, Collection<Node>> entry : children.asMap().entrySet()) {
			if (isOrdered(parent, entry.getKey()) == ordered) {
				descendants.putAll(entry.getKey(), entry.getValue());
			}
			for (Node child : entry.getValue()) {
				descendants.putAll(getDescendants(child, ordered));
			}
		}
		return descendants;
	}

	@Override
	public Node getValue(Node node) {
		return node;
	}

	@Override
	public boolean isOrdered(Node node, String childType) {
		// TODO optimize here
		return ASTModelBridges.getBridge(node).isOrdered(childType);
	}

	@Override
	public boolean isFirstChild(Node node) {
		// TODO optimize here
		Node parent = ASTNodeData.parent(node);
		ListMultimap<String, Node> childrenByType = getChildren(parent, true);
		for (Collection<Node> children : childrenByType.asMap().values()) {
			if (((List<Node>) children).get(0) == node) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isLastChild(Node node) {
		// TODO optimize here
		Node parent = ASTNodeData.parent(node);
		ListMultimap<String, Node> childrenByType = getChildren(parent, true);
		for (Collection<Node> children : childrenByType.asMap().values()) {
			if (((List<Node>) children).get(children.size() - 1) == node) {
				return true;
			}
		}
		return false;
	}

}

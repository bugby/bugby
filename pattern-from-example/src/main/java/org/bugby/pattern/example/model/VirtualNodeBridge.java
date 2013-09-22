package org.bugby.pattern.example.model;

import japa.parser.ast.Node;

import java.util.List;

import org.bugby.pattern.example.ASTModelBridge;
import org.bugby.pattern.example.VirtualNode;

import com.google.common.base.Objects;

public class VirtualNodeBridge implements ASTModelBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		VirtualNode n = (VirtualNode) parent;
		return n.getChildren();
	}

	@Override
	public boolean isOrdered(Node node) {
		return ((VirtualNode) node).isOrdered();
	}

	@Override
	public String getMatcherName(Node node) {
		return "";
	}

	public boolean areSimilar(Node patternNode, Node sourceNode) {
		VirtualNode pattern = (VirtualNode) patternNode;
		VirtualNode source = (VirtualNode) sourceNode;
		return Objects.equal(pattern.getName(), source.getName());
	}

}

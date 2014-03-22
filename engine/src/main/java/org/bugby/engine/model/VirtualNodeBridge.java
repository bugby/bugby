package org.bugby.engine.model;

import japa.parser.ast.Node;

import java.util.List;

import org.bugby.engine.ASTModelBridge;
import org.bugby.engine.VirtualNode;

import com.google.common.base.Objects;

public class VirtualNodeBridge implements ASTModelBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		VirtualNode n = (VirtualNode) parent;
		return n.getChildren();
	}

	@Override
	public boolean isOrdered(String node) {
		return false;
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

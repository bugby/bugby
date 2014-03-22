package org.bugby.engine.model;

import japa.parser.ast.Node;

import java.util.List;

import org.bugby.engine.ASTModelBridge;

public class IgnoreBridge implements ASTModelBridge {

	@Override
	public List<Node> getChildren(Node parent) {
		return null;
	}

	@Override
	public boolean isOrdered(String node) {
		return true;
	}

	@Override
	public String getMatcherName(Node node) {
		return "";
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		return true;
	}

}

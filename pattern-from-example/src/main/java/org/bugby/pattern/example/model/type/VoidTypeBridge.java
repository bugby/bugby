package org.bugby.pattern.example.model.type;

import japa.parser.ast.Node;

import java.util.Collections;
import java.util.List;

import org.bugby.pattern.example.ASTModelBridge;

public class VoidTypeBridge implements ASTModelBridge {

	@Override
	public List<Node> getChildren(Node parent) {
		return Collections.emptyList();
	}

	@Override
	public boolean isOrdered(Node node) {
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

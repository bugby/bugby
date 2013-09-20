package org.bugby.pattern.example.model.body;

import japa.parser.ast.Node;

import java.util.Collections;
import java.util.List;

import org.bugby.pattern.example.ASTModelBridge;

public class VariableDeclaratorIdBridge implements ASTModelBridge {

	@Override
	public List<Node> getChildren(Node parent) {
		return Collections.emptyList();
	}

	@Override
	public boolean isOrdered(Node node) {
		return false;
	}

	@Override
	public String getMatcherName(Node node) {
		return null;
	}

}

package org.bugby.engine.model.body;

import japa.parser.ast.Node;
import japa.parser.ast.body.VariableDeclaratorId;

import java.util.Collections;
import java.util.List;

import org.bugby.engine.ASTModelBridge;

public class VariableDeclaratorIdBridge implements ASTModelBridge {

	@Override
	public List<Node> getChildren(Node parent) {
		return Collections.emptyList();
	}

	@Override
	public boolean isOrdered(String childType) {
		return false;
	}

	@Override
	public String getMatcherName(Node node) {
		return null;
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		VariableDeclaratorId patternDecl = (VariableDeclaratorId) patternNode;
		VariableDeclaratorId sourceDecl = (VariableDeclaratorId) sourceNode;
		return patternDecl.getName().equals(sourceDecl.getName());
	}

}
package org.bugby.pattern.example.model.body;

import japa.parser.ast.Node;
import japa.parser.ast.body.VariableDeclarator;

import java.util.List;

import org.bugby.pattern.example.ASTModelBridge;
import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

public class VariableDeclaratorBridge implements ASTModelBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		VariableDeclarator decl = (VariableDeclarator) parent;

		return (List) ListUtils.asList(VirtualNode.of("id", decl.getId()), VirtualNode.of("init", decl.getInit()));
	}

	@Override
	public boolean isOrdered(Node node) {
		return false;
	}

	@Override
	public String getMatcherName(Node node) {
		VariableDeclarator decl = (VariableDeclarator) node;
		return decl.getId().getName();
	}

}

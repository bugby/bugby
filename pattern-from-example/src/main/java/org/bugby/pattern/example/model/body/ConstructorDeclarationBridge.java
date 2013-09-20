package org.bugby.pattern.example.model.body;

import japa.parser.ast.Node;
import japa.parser.ast.body.ConstructorDeclaration;

import java.util.List;

import org.bugby.pattern.example.ASTModelBridge;
import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

public class ConstructorDeclarationBridge implements ASTModelBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {

		ConstructorDeclaration decl = (ConstructorDeclaration) parent;

		return (List) ListUtils.asList(VirtualNode.of("typeParameters", decl.getTypeParameters()), VirtualNode.of(
				"parameters", decl.getParameters()), VirtualNode.of("throws", decl.getThrows()), VirtualNode.of(
				"block", decl.getBlock()), VirtualNode.of("annotations", decl.getAnnotations()));
	}

	@Override
	public boolean isOrdered(Node node) {
		return false;
	}

	@Override
	public String getMatcherName(Node node) {
		return "";
	}
}

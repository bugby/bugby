package org.bugby.pattern.example.model.body;

import japa.parser.ast.Node;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;

import java.util.List;

import org.bugby.pattern.example.ASTModelBridge;
import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

public class ClassOrInterfaceDeclarationBridge implements ASTModelBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		ClassOrInterfaceDeclaration decl = (ClassOrInterfaceDeclaration) parent;
		return (List) ListUtils.asList(VirtualNode.of("typeParameters", decl.getTypeParameters()), VirtualNode.of(
				"extends", decl.getExtends()), VirtualNode.of("implements", decl.getImplements()), VirtualNode.of(
				"members", decl.getMembers()), VirtualNode.of("annotations", decl.getAnnotations()));
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

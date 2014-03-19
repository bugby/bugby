package org.bugby.engine.model.body;

import japa.parser.ast.Node;
import japa.parser.ast.body.MethodDeclaration;

import java.util.List;

import org.bugby.engine.ASTModelBridge;
import org.bugby.engine.VirtualNode;
import org.bugby.engine.model.ListUtils;

public class MethodDeclarationBridge implements ASTModelBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {

		MethodDeclaration decl = (MethodDeclaration) parent;
		return (List) ListUtils.asList(VirtualNode.of(decl, "typeParameters", decl.getTypeParameters(), true),
				VirtualNode.of(decl, "parameters", decl.getParameters(), true), VirtualNode.of(decl, "throws", decl.getThrows(), true),
				VirtualNode.of(decl, "body", decl.getBody(), true), VirtualNode.of(decl, "annotations", decl.getAnnotations(), true));
	}

	@Override
	public boolean isOrdered(String childType) {
		return childType.equals("parameters") || childType.equals("typeParameters");
	}

	@Override
	public String getMatcherName(Node node) {
		MethodDeclaration decl = (MethodDeclaration) node;
		return decl.getName();
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		MethodDeclaration patternDecl = (MethodDeclaration) patternNode;
		MethodDeclaration sourceDecl = (MethodDeclaration) sourceNode;
		// TODO skip or not the modifiers
		return patternDecl.getName().equals(sourceDecl.getName()) && patternDecl.getArrayCount() == sourceDecl.getArrayCount();
	}
}

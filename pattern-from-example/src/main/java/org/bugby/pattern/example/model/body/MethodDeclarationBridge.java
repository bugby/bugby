package org.bugby.pattern.example.model.body;

import japa.parser.ast.Node;
import japa.parser.ast.body.MethodDeclaration;

import java.util.List;

import org.bugby.pattern.example.ASTModelBridge;
import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

public class MethodDeclarationBridge implements ASTModelBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {

		MethodDeclaration decl = (MethodDeclaration) parent;
		// TODO fix this using factories and annotations
		return (List) ListUtils.asList(VirtualNode.of(decl, "typeParameters", decl.getTypeParameters(), true),
				VirtualNode.of(decl, "parameters", decl.getParameters(), true),
				VirtualNode.of(decl, "throws", decl.getThrows(), true),
				VirtualNode.of(decl, "body", decl.getBody(), true),
				VirtualNode.of(decl, "annotations", decl.getAnnotations(), true));
		// return (List) ListUtils.singletonListOrEmpty(decl.getBody());
	}

	@Override
	public boolean isOrdered(Node node) {
		return false;
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
		// TODO add other checks on parameters
		return patternDecl.getName().equals(sourceDecl.getName());
	}
}

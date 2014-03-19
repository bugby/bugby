package org.bugby.engine.model.body;

import japa.parser.ast.Node;
import japa.parser.ast.body.ConstructorDeclaration;

import java.util.List;

import org.bugby.engine.ASTModelBridge;
import org.bugby.engine.VirtualNode;
import org.bugby.engine.model.ListUtils;

public class ConstructorDeclarationBridge implements ASTModelBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {

		ConstructorDeclaration decl = (ConstructorDeclaration) parent;

		return (List) ListUtils.asList(VirtualNode.of(parent, "typeParameters", decl.getTypeParameters(), false),
				VirtualNode.of(parent, "parameters", decl.getParameters(), false),
				VirtualNode.of(parent, "throws", decl.getThrows(), false),
				VirtualNode.of(parent, "block", decl.getBlock(), false),
				VirtualNode.of(parent, "annotations", decl.getAnnotations(), false));
	}

	@Override
	public boolean isOrdered(Node node) {
		return false;
	}

	@Override
	public String getMatcherName(Node node) {
		return "";
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		ConstructorDeclaration patternDecl = (ConstructorDeclaration) patternNode;
		ConstructorDeclaration sourceDecl = (ConstructorDeclaration) sourceNode;
		// TODO add other checks on parameters
		return patternDecl.getName().equals(sourceDecl.getName());
	}
}

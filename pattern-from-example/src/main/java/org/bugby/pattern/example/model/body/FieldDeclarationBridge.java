package org.bugby.pattern.example.model.body;

import japa.parser.ast.Node;
import japa.parser.ast.body.FieldDeclaration;

import java.util.List;

import org.bugby.pattern.example.ASTModelBridge;
import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

public class FieldDeclarationBridge implements ASTModelBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {

		FieldDeclaration decl = (FieldDeclaration) parent;
		return (List) ListUtils.asList(VirtualNode.of(decl, "type", decl.getType(), true),
				VirtualNode.of(decl, "variables", decl.getVariables(), true));
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
		FieldDeclaration patternDecl = (FieldDeclaration) patternNode;
		FieldDeclaration sourceDecl = (FieldDeclaration) sourceNode;
		// TODO skip or not the modifiers
		return patternDecl.getModifiers() == sourceDecl.getModifiers();
	}
}

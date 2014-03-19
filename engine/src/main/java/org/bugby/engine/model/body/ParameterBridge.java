package org.bugby.engine.model.body;

import japa.parser.ast.Node;
import japa.parser.ast.body.Parameter;

import java.util.List;

import org.bugby.engine.ASTModelBridge;
import org.bugby.engine.VirtualNode;
import org.bugby.engine.model.ListUtils;

public class ParameterBridge implements ASTModelBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		Parameter decl = (Parameter) parent;

		return (List) ListUtils.asList(VirtualNode.of(parent, "annotations", decl.getAnnotations(), true),
				VirtualNode.of(parent, "id", decl.getId(), true), VirtualNode.of(parent, "type", decl.getType(), true));
	}

	@Override
	public boolean isOrdered(Node node) {
		return true;
	}

	@Override
	public String getMatcherName(Node node) {
		Parameter decl = (Parameter) node;
		return decl.getId().getName();
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		Parameter patternDecl = (Parameter) patternNode;
		Parameter sourceDecl = (Parameter) sourceNode;
		return patternDecl.isVarArgs() == sourceDecl.isVarArgs()
				&& patternDecl.getModifiers() == sourceDecl.getModifiers();
	}
}

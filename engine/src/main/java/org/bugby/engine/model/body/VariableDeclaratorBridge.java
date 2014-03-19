package org.bugby.engine.model.body;

import japa.parser.ast.Node;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.expr.VariableDeclarationExpr;

import java.util.List;

import org.bugby.engine.ASTModelBridge;
import org.bugby.engine.VirtualNode;
import org.bugby.engine.model.ListUtils;
import org.bugby.engine.model.NodeUtils;
import org.bugby.wildcard.Wildcards.IgnoreInitialization;
import org.richast.node.ASTNodeData;

public class VariableDeclaratorBridge implements ASTModelBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		VariableDeclarator decl = (VariableDeclarator) parent;
		VariableDeclarationExpr declExpression = ASTNodeData.parent(decl, VariableDeclarationExpr.class);
		if (NodeUtils.hasAnnotation(declExpression, IgnoreInitialization.class)) {
			return (List) ListUtils.asList(VirtualNode.of(parent, "id", decl.getId(), true));
		}

		return (List) ListUtils.asList(VirtualNode.of(parent, "id", decl.getId(), true), VirtualNode.of(parent, "init", decl.getInit(), true));
	}

	@Override
	public boolean isOrdered(String childType) {
		return false;
	}

	@Override
	public String getMatcherName(Node node) {
		VariableDeclarator decl = (VariableDeclarator) node;
		return decl.getId().getName();
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		// send the check to children
		return true;
	}
}

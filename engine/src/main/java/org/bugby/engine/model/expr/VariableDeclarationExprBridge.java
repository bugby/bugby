package org.bugby.engine.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.VariableDeclarationExpr;

import java.util.List;

import org.bugby.engine.VirtualNode;
import org.bugby.engine.model.ListUtils;

public class VariableDeclarationExprBridge extends ExpressionBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {

		VariableDeclarationExpr expr = (VariableDeclarationExpr) parent;

		return (List) ListUtils.asList(VirtualNode.of(parent, "annotations", expr.getAnnotations(), false),
				VirtualNode.of(parent, "type", expr.getType(), true),
				VirtualNode.of(parent, "vars", expr.getVars(), true));
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		// i may check the modifiers !?
		return true;
	}
}

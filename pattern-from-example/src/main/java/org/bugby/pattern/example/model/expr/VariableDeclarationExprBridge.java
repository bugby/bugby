package org.bugby.pattern.example.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.VariableDeclarationExpr;

import java.util.List;

import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

public class VariableDeclarationExprBridge extends ExpressionBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {

		VariableDeclarationExpr expr = (VariableDeclarationExpr) parent;

		return (List) ListUtils.asList(VirtualNode.of("annotations", expr.getAnnotations()),
				VirtualNode.of("type", expr.getType()), VirtualNode.of("vars", expr.getVars()));
	}

}

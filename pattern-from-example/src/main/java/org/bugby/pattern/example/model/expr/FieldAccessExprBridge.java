package org.bugby.pattern.example.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.FieldAccessExpr;

import java.util.List;

import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

public class FieldAccessExprBridge extends ExpressionBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		FieldAccessExpr expr = (FieldAccessExpr) parent;

		return (List) ListUtils.asList(VirtualNode.of("scope", expr.getScope()),
				VirtualNode.of("typeArgs", expr.getTypeArgs()));
	}

}

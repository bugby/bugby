package org.bugby.pattern.example.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.MethodCallExpr;

import java.util.List;

import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

public class MethodCallExprBridge extends ExpressionBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		MethodCallExpr expr = (MethodCallExpr) parent;

		return (List) ListUtils.asList(VirtualNode.of("scope", expr.getScope()),
				VirtualNode.of("typeArgs", expr.getTypeArgs()), VirtualNode.of("args", expr.getArgs()));
	}

	@Override
	public String getMatcherName(Node node) {
		MethodCallExpr expr = (MethodCallExpr) node;
		return expr.getName();
	}

}

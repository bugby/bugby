package org.bugby.pattern.example.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.BinaryExpr;

import java.util.List;

import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

public class BinaryExprBridge extends ExpressionBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		BinaryExpr expr = (BinaryExpr) parent;

		return (List) ListUtils.asList(VirtualNode.of("left", expr.getLeft()), VirtualNode.of("right", expr.getRight()));
	}

}

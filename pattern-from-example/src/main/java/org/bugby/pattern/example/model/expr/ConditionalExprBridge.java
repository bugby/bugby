package org.bugby.pattern.example.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.ConditionalExpr;

import java.util.List;

import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

public class ConditionalExprBridge extends ExpressionBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		ConditionalExpr expr = (ConditionalExpr) parent;

		return (List) ListUtils.asList(VirtualNode.of(parent, "condition", expr.getCondition(), true),
				VirtualNode.of(parent, "then", expr.getThenExpr(), true),
				VirtualNode.of(parent, "else", expr.getElseExpr(), true));
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		return true;
	}
}

package org.bugby.engine.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.UnaryExpr;

import java.util.List;

import org.bugby.engine.VirtualNode;
import org.bugby.engine.model.ListUtils;

public class UnaryExprBridge extends ExpressionBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		UnaryExpr expr = (UnaryExpr) parent;

		return (List) ListUtils.asList(VirtualNode.of(parent, "expression", expr.getExpr(), true));
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		UnaryExpr patternExpr = (UnaryExpr) patternNode;
		UnaryExpr sourceExpr = (UnaryExpr) sourceNode;
		return patternExpr.getOperator().equals(sourceExpr.getOperator());
	}
}

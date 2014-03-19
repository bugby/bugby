package org.bugby.engine.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.BinaryExpr;

import java.util.List;

import org.bugby.engine.VirtualNode;
import org.bugby.engine.model.ListUtils;

public class BinaryExprBridge extends ExpressionBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		BinaryExpr expr = (BinaryExpr) parent;

		// TODO here the order depends on the type of the operator
		return (List) ListUtils.asList(VirtualNode.of(parent, "left", expr.getLeft(), true),
				VirtualNode.of(parent, "right", expr.getRight(), true));
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		BinaryExpr patternExpr = (BinaryExpr) patternNode;
		BinaryExpr sourceExpr = (BinaryExpr) sourceNode;
		return patternExpr.getOperator().equals(sourceExpr.getOperator());
	}
}

package org.bugby.pattern.example.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.AssignExpr;

import java.util.List;

import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

public class AssignExprBridge extends ExpressionBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		AssignExpr expr = (AssignExpr) parent;

		return (List) ListUtils.asList(VirtualNode.of(parent, "target", expr.getTarget(), true),
				VirtualNode.of(parent, "value", expr.getValue(), true));
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		AssignExpr patternExpr = (AssignExpr) patternNode;
		AssignExpr sourceExpr = (AssignExpr) sourceNode;
		// TODO the operator may be optional
		return patternExpr.getOperator().equals(sourceExpr.getOperator());
	}
}

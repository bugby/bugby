package org.bugby.pattern.example.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.CastExpr;

import java.util.List;

import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

public class CastExprBridge extends ExpressionBridge {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		CastExpr expr = (CastExpr) parent;

		return (List) ListUtils.asList(VirtualNode.of(parent, "type", expr.getType(), true),
				VirtualNode.of(parent, "expression", expr.getExpr(), true));
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		return true;
	}

}

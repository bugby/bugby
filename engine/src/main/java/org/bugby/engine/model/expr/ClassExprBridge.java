package org.bugby.engine.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.ClassExpr;

import java.util.List;

import org.bugby.engine.model.ListUtils;

public class ClassExprBridge extends ExpressionBridge {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		ClassExpr expr = (ClassExpr) parent;

		return (List) ListUtils.singletonListOrEmpty(expr.getType());
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		return true;
	}

}

package org.bugby.engine.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.EnclosedExpr;

import java.util.List;

import org.bugby.engine.model.ListUtils;

public class EnclosedExprBridge extends ExpressionBridge {

	@Override
	public List<Node> getChildren(Node parent) {
		return ListUtils.singletonListOrEmpty((Node) ((EnclosedExpr) parent).getInner());
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		return true;
	}
}

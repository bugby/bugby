package org.bugby.engine.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.SuperExpr;

import java.util.List;

import org.bugby.engine.model.ListUtils;

public class SuperExprBridge extends ExpressionBridge {

	@Override
	public List<Node> getChildren(Node parent) {
		return ListUtils.singletonListOrEmpty((Node) ((SuperExpr) parent).getClassExpr());
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		return true;
	}
}

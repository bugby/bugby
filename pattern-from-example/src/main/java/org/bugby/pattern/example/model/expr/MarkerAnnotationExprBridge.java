package org.bugby.pattern.example.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.MarkerAnnotationExpr;

import java.util.List;

import org.bugby.pattern.example.model.ListUtils;

public class MarkerAnnotationExprBridge extends ExpressionBridge {

	@Override
	public List<Node> getChildren(Node parent) {
		return ListUtils.singletonListOrEmpty((Node) ((MarkerAnnotationExpr) parent).getName());
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		// let the children decide
		return true;
	}
}

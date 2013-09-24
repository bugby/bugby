package org.bugby.pattern.example.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.MarkerAnnotationExpr;

import java.util.List;

import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

public class MarkerAnnotationExprBridge extends ExpressionBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		MarkerAnnotationExpr expr = (MarkerAnnotationExpr) parent;

		return (List) ListUtils.asList(VirtualNode.of(parent, "name", expr.getName(), true));
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		// let the children decide
		return true;
	}

}

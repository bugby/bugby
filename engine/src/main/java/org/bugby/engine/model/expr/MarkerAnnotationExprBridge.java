package org.bugby.engine.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.MarkerAnnotationExpr;

import java.util.List;

import org.bugby.engine.VirtualNode;
import org.bugby.engine.model.ListUtils;

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

package org.bugby.engine.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.NormalAnnotationExpr;

import java.util.List;

import org.bugby.engine.VirtualNode;
import org.bugby.engine.model.ListUtils;

public class NormalAnnotationExprBridge extends ExpressionBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		NormalAnnotationExpr expr = (NormalAnnotationExpr) parent;

		return (List) ListUtils.asList(VirtualNode.of(parent, "name", expr.getName(), true),
				VirtualNode.of(parent, "pairs", expr.getPairs(), true));
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		// let the children decide
		return true;
	}

	@Override
	public String getMatcherName(Node node) {
		return null;
	}
}

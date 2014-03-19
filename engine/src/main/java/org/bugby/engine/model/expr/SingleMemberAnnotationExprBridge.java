package org.bugby.engine.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;

import java.util.List;

import org.bugby.engine.VirtualNode;
import org.bugby.engine.model.ListUtils;

public class SingleMemberAnnotationExprBridge extends ExpressionBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		SingleMemberAnnotationExpr expr = (SingleMemberAnnotationExpr) parent;

		return (List) ListUtils.asList(VirtualNode.of(parent, "name", expr.getName(), true));
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		// let the children decide
		return true;
	}

}

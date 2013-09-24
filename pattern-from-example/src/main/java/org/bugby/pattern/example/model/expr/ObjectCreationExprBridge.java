package org.bugby.pattern.example.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.ObjectCreationExpr;

import java.util.List;

import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

public class ObjectCreationExprBridge extends ExpressionBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		ObjectCreationExpr expr = (ObjectCreationExpr) parent;

		return (List) ListUtils.asList(VirtualNode.of(parent, "scope", expr.getScope(), true),
				VirtualNode.of(parent, "type", expr.getType(), true),
				VirtualNode.of(parent, "typeArgs", expr.getTypeArgs(), true),
				VirtualNode.of(parent, "args", expr.getArgs(), true),
				VirtualNode.of(parent, "anonymousClassBody", expr.getAnonymousClassBody(), true));
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		return true;
	}
}

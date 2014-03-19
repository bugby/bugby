package org.bugby.engine.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.MethodCallExpr;

import java.util.List;

import org.bugby.engine.VirtualNode;
import org.bugby.engine.model.ListUtils;

import com.google.common.base.Objects;

public class MethodCallExprBridge extends ExpressionBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		MethodCallExpr expr = (MethodCallExpr) parent;

		return (List) ListUtils.asList(VirtualNode.of(parent, "scope", expr.getScope(), true),
				VirtualNode.of(parent, "typeArgs", expr.getTypeArgs(), true),
				VirtualNode.of(parent, "args", expr.getArgs(), true));
	}

	@Override
	public String getMatcherName(Node node) {
		MethodCallExpr expr = (MethodCallExpr) node;
		return expr.getName();
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		MethodCallExpr patternExpr = (MethodCallExpr) patternNode;
		MethodCallExpr sourceExpr = (MethodCallExpr) sourceNode;
		// TODO i need to check also the parameters
		return Objects.equal(patternExpr.getName(), sourceExpr.getName());
	}

}

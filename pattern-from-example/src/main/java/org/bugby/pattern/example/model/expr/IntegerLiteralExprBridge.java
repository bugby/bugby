package org.bugby.pattern.example.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.IntegerLiteralExpr;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Objects;

public class IntegerLiteralExprBridge extends ExpressionBridge {

	@Override
	public List<Node> getChildren(Node parent) {
		return Collections.emptyList();
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		IntegerLiteralExpr patternExpr = (IntegerLiteralExpr) patternNode;
		IntegerLiteralExpr sourceExpr = (IntegerLiteralExpr) sourceNode;
		return Objects.equal(patternExpr.getValue(), sourceExpr.getValue());
	}
}

package org.bugby.engine.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.StringLiteralExpr;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Objects;

public class StringLiteralExprBridge extends ExpressionBridge {

	@Override
	public List<Node> getChildren(Node parent) {
		return Collections.emptyList();
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		StringLiteralExpr patternExpr = (StringLiteralExpr) patternNode;
		StringLiteralExpr sourceExpr = (StringLiteralExpr) sourceNode;
		return Objects.equal(patternExpr.getValue(), sourceExpr.getValue());
	}
}

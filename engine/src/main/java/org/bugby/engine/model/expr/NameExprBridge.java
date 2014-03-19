package org.bugby.engine.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.NameExpr;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Objects;

public class NameExprBridge extends ExpressionBridge {

	@Override
	public List<Node> getChildren(Node parent) {
		return Collections.emptyList();
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		NameExpr patternExpr = (NameExpr) patternNode;
		NameExpr sourceExpr = (NameExpr) sourceNode;
		return Objects.equal(patternExpr.getName(), sourceExpr.getName());
	}

	@Override
	public String getMatcherName(Node node) {
		NameExpr patternExpr = (NameExpr) node;
		return patternExpr.getName();
	}
}

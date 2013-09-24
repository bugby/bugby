package org.bugby.pattern.example.model.expr;

import japa.parser.ast.Node;

import java.util.Collections;
import java.util.List;

public class NullLiteralExprBridge extends ExpressionBridge {

	@Override
	public List<Node> getChildren(Node parent) {
		return Collections.emptyList();
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		return true;
	}
}

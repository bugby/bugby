package org.bugby.pattern.example.model.expr;

import japa.parser.ast.Node;

import java.util.Collections;
import java.util.List;

public class NameExprBridge extends ExpressionBridge {

	@Override
	public List<Node> getChildren(Node parent) {
		return Collections.emptyList();
	}

}

package org.bugby.engine.model.expr;

import japa.parser.ast.Node;

import org.bugby.engine.ASTModelBridge;

abstract public class ExpressionBridge implements ASTModelBridge {
	@Override
	public boolean isOrdered(Node node) {
		return true;
	}

	@Override
	public String getMatcherName(Node node) {
		return "";
	}
}

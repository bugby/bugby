package org.bugby.pattern.example.model.expr;

import japa.parser.ast.Node;

import org.bugby.pattern.example.ASTModelBridge;

abstract public class ExpressionBridge implements ASTModelBridge {
	@Override
	public boolean isOrdered(Node node) {
		return true;
	}

	@Override
	public String getMatcherName(Node node) {
		return null;
	}
}

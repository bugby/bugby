package org.bugby.pattern.example.model.expr;

import japa.parser.ast.Node;

import org.bugby.pattern.example.ASTModelBridge;

abstract public class ExpressionBridge implements ASTModelBridge {
	@Override
	public boolean isOrdered(Node node) {
		// TODO - check here is it child of a binary op for which order does not count (i.e. +)
		return true;
	}

	@Override
	public String getMatcherName(Node node) {
		return null;
	}
}

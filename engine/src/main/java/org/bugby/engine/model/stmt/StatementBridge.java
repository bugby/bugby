package org.bugby.engine.model.stmt;

import japa.parser.ast.Node;

import org.bugby.engine.ASTModelBridge;

abstract public class StatementBridge implements ASTModelBridge {
	@Override
	public boolean isOrdered(Node node) {
		return true;
	}

	@Override
	public String getMatcherName(Node node) {
		return null;
	}
}

package org.bugby.pattern.example.model.stmt;

import japa.parser.ast.Node;
import japa.parser.ast.stmt.ExpressionStmt;

import java.util.List;

import org.bugby.pattern.example.model.ListUtils;

public class ExpressionStmtBridge extends StatementBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		ExpressionStmt stmt = (ExpressionStmt) parent;
		return ListUtils.singletonListOrEmpty((Node) stmt.getExpression());
	}
}

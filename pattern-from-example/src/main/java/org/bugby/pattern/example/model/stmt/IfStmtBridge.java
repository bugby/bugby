package org.bugby.pattern.example.model.stmt;

import japa.parser.ast.Node;
import japa.parser.ast.stmt.IfStmt;

import java.util.List;

import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

public class IfStmtBridge extends StatementBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		IfStmt stmt = (IfStmt) parent;
		return (List) ListUtils.asList(VirtualNode.of("condition", stmt.getCondition()),
				VirtualNode.of("then", stmt.getThenStmt()), VirtualNode.of("else", stmt.getElseStmt()));
	}

	@Override
	public String getMatcherName(Node node) {
		return "";
	}
}

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
		return (List) ListUtils.asList(VirtualNode.of(parent, "condition", stmt.getCondition(), true),
				VirtualNode.of(parent, "then", stmt.getThenStmt(), true),
				VirtualNode.of(parent, "else", stmt.getElseStmt(), true));
	}

	@Override
	public String getMatcherName(Node node) {
		return "";
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		// let the children decide
		return true;
	}
}

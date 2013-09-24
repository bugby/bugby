package org.bugby.pattern.example.model.stmt;

import japa.parser.ast.Node;
import japa.parser.ast.stmt.ReturnStmt;

import java.util.List;

import org.bugby.pattern.example.model.ListUtils;

public class ReturnStmtBridge extends StatementBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		ReturnStmt stmt = (ReturnStmt) parent;
		return ListUtils.singletonListOrEmpty((Node) stmt.getExpr());
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		// let the children decide
		return true;
	}

	@Override
	public String getMatcherName(Node node) {
		return "";
	}
}

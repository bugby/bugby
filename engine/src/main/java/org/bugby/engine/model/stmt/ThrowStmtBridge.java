package org.bugby.engine.model.stmt;

import japa.parser.ast.Node;
import japa.parser.ast.stmt.ThrowStmt;

import java.util.List;

import org.bugby.engine.model.ListUtils;

public class ThrowStmtBridge extends StatementBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		ThrowStmt stmt = (ThrowStmt) parent;
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

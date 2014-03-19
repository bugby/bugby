package org.bugby.engine.model.stmt;

import japa.parser.ast.Node;
import japa.parser.ast.stmt.ForStmt;

import java.util.List;

import org.bugby.engine.VirtualNode;
import org.bugby.engine.model.ListUtils;

public class ForStmtBridge extends StatementBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		ForStmt stmt = (ForStmt) parent;
		return (List) ListUtils.asList(VirtualNode.of(parent, "init", stmt.getInit(), true),
				VirtualNode.of(parent, "compare", stmt.getCompare(), true),
				VirtualNode.of(parent, "update", stmt.getUpdate(), true),
				VirtualNode.of(parent, "body", stmt.getBody(), true));
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

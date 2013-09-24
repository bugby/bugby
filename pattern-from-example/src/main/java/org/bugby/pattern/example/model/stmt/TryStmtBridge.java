package org.bugby.pattern.example.model.stmt;

import japa.parser.ast.Node;
import japa.parser.ast.stmt.TryStmt;

import java.util.List;

import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

public class TryStmtBridge extends StatementBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		TryStmt stmt = (TryStmt) parent;
		return (List) ListUtils.asList(VirtualNode.of(parent, "try", stmt.getTryBlock(), true),
				VirtualNode.of(parent, "catch", stmt.getCatchs(), true),
				VirtualNode.of(parent, "finally", stmt.getFinallyBlock(), true));
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

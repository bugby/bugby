package org.bugby.pattern.example.model.stmt;

import japa.parser.ast.Node;
import japa.parser.ast.stmt.BlockStmt;

import java.util.List;

import org.bugby.pattern.example.model.ListUtils;

public class BlockStmtBridge extends StatementBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		BlockStmt stmt = (BlockStmt) parent;
		return ListUtils.notNull((List) stmt.getStmts());
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		// let the children decide
		return true;
	}
}

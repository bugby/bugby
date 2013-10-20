package org.bugby.pattern.example.model.stmt;

import japa.parser.ast.Node;

import java.util.Collections;
import java.util.List;

public class ContinueStmtBridge extends StatementBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		return Collections.emptyList();
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

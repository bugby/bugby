package org.bugby.pattern.example.model.stmt;

import japa.parser.ast.Node;
import japa.parser.ast.stmt.CatchClause;

import java.util.List;

import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

public class CatchClauseBridge extends StatementBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		CatchClause stmt = (CatchClause) parent;
		return (List) ListUtils.asList(VirtualNode.of(parent, "exception", stmt.getExcept(), true),
				VirtualNode.of(parent, "catchBlock", stmt.getCatchBlock(), true));
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

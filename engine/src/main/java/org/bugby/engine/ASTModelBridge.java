package org.bugby.engine;

import japa.parser.ast.Node;

import java.util.List;

public interface ASTModelBridge {
	public List<Node> getChildren(Node parent);

	public boolean isOrdered(Node node);

	public String getMatcherName(Node node);

	public boolean areSimilar(Node patternNode, Node sourceNode);
}

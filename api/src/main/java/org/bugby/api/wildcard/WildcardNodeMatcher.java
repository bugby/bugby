package org.bugby.api.wildcard;

import japa.parser.ast.Node;

import org.bugby.matcher.tree.MatchingType;
import org.bugby.matcher.tree.TreeModel;

public interface WildcardNodeMatcher {
	public boolean matches(TreeModel<Node, Node> treeModel, Node node, MatchingContext context);

	public boolean isOrdered();

	public MatchingType getMatchingType();
}

package org.bugby.wildcard.api;

import japa.parser.ast.Node;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.matcher.acr.TreeModel;

public interface WildcardNodeMatcher {
	public boolean matches(TreeModel<Node, Node> treeModel, Node node, MatchingContext context);

	public boolean isOrdered();

	public MatchingType getMatchingType();
}

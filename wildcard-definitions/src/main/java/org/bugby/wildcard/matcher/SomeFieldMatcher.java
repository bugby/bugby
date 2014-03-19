package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.body.VariableDeclarator;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.WildcardNodeMatcher;
import org.bugby.matcher.tree.MatchingType;
import org.bugby.matcher.tree.TreeModel;

public class SomeFieldMatcher implements WildcardNodeMatcher {

	@Override
	public boolean matches(TreeModel<Node, Node> treeModel, Node node, MatchingContext context) {
		return node instanceof VariableDeclarator;
	}

	@Override
	public boolean isOrdered() {
		return false;
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.normal;
	}
}

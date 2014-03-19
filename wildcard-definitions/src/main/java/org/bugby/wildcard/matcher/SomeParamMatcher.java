package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.body.Parameter;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.WildcardNodeMatcher;
import org.bugby.matcher.tree.MatchingType;
import org.bugby.matcher.tree.TreeModel;

public class SomeParamMatcher implements WildcardNodeMatcher {

	@Override
	public boolean matches(TreeModel<Node, Node> treeModel, Node node, MatchingContext context) {
		return node instanceof Parameter;
	}

	@Override
	public boolean isOrdered() {
		return true;
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.normal;
	}
}

package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.body.VariableDeclarator;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.wildcard.api.MatchingContext;
import org.bugby.wildcard.api.WildcardNodeMatcher;

public class SomeFieldMatcher implements WildcardNodeMatcher {

	@Override
	public boolean matches(Node node, MatchingContext context) {
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

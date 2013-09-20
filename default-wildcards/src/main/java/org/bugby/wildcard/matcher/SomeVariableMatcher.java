package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.body.VariableDeclaratorId;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.wildcard.api.WildcardNodeMatcher;

public class SomeVariableMatcher implements WildcardNodeMatcher {

	@Override
	public boolean matches(Node node) {
		return node instanceof VariableDeclaratorId;
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

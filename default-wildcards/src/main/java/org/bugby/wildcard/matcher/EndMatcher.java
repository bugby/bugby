package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.wildcard.api.WildcardNodeMatcher;

public class EndMatcher implements WildcardNodeMatcher {

	@Override
	public boolean matches(Node node) {
		return false;
	}

	@Override
	public boolean isOrdered() {
		return true;
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.end;
	}
}

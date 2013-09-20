package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.type.ClassOrInterfaceType;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.wildcard.api.WildcardNodeMatcher;

public class SomeTypeMatcher implements WildcardNodeMatcher {

	@Override
	public boolean matches(Node node) {
		return node instanceof ClassOrInterfaceType;
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

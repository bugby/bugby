package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.wildcard.api.WildcardNodeMatcher;

public class SomeTypeDeclarationMatcher implements WildcardNodeMatcher {

	@Override
	public boolean matches(Node node) {
		return node instanceof ClassOrInterfaceDeclaration;
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

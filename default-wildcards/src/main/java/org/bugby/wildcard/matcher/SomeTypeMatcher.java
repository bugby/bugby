package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.type.ClassOrInterfaceType;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.wildcard.WildcardNodeMatcherFromExample;

public class SomeTypeMatcher implements WildcardNodeMatcherFromExample {
	private boolean ordered;

	@Override
	public void init(Node nodeFromExample) {
		ordered = (nodeFromExample instanceof ClassOrInterfaceType);
	}

	@Override
	public boolean matches(Node node) {
		return node instanceof ClassOrInterfaceType || node instanceof ClassOrInterfaceDeclaration;
	}

	@Override
	public boolean isOrdered() {
		return ordered;
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.normal;
	}
}

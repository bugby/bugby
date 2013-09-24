package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.type.Type;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.wildcard.api.WildcardNodeMatcher;

public class SomeTypeMatcher implements WildcardNodeMatcher {
	private final boolean ordered;

	public SomeTypeMatcher(Node nodeFromExample) {
		ordered = (nodeFromExample instanceof Type);
	}

	@Override
	public boolean matches(Node node) {
		return node instanceof Type || node instanceof ClassOrInterfaceDeclaration;
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

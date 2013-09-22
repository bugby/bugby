package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.MethodCallExpr;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.wildcard.WildcardNodeMatcherFromExample;

public class SomeMethodMatcher implements WildcardNodeMatcherFromExample {
	private boolean ordered;

	@Override
	public void init(Node nodeFromExample) {
		ordered = (nodeFromExample instanceof MethodCallExpr);
	}

	@Override
	public boolean matches(Node node) {
		return node instanceof MethodCallExpr || node instanceof MethodDeclaration;
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

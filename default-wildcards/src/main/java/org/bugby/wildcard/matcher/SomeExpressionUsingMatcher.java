package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.expr.Expression;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.wildcard.api.MatchingContext;
import org.bugby.wildcard.api.WildcardNodeMatcher;

public class SomeExpressionUsingMatcher implements WildcardNodeMatcher {

	@Override
	public boolean matches(Node node, MatchingContext context) {
		return node instanceof Expression;
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

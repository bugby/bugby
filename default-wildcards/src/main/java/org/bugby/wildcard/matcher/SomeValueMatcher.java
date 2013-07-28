package org.bugby.wildcard.matcher;

import japa.parser.ast.expr.Expression;

import org.bugby.wildcard.api.WildcardNodeMatcher;

public class SomeValueMatcher implements WildcardNodeMatcher<Expression> {

	@Override
	public boolean matches(Expression node) {
		return true;
	}

}

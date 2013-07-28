package org.bugby.wildcard.matcher;

import japa.parser.ast.expr.Expression;

import org.bugby.wildcard.api.WildcardNodeMatcher;

public class SomeTypedValueMatcher implements WildcardNodeMatcher<Expression> {
	private final Class<?> checkType;

	public SomeTypedValueMatcher(Class<?> checkType) {
		this.checkType = checkType;
	}

	@Override
	public boolean matches(Expression node) {
		return true;
	}
}

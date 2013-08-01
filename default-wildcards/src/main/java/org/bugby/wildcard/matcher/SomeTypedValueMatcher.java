package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.expr.Expression;

import org.bugby.wildcard.WildcardNodeMatcherFromExample;

public class SomeTypedValueMatcher implements WildcardNodeMatcherFromExample<Expression> {
	private Class<?> checkType;

	@Override
	public boolean matches(Expression node) {
		return true;
	}

	@Override
	public void init(Node nodeFromExample) {
		// TODO Auto-generated method stub
		// Class<?> checkType
	}
}

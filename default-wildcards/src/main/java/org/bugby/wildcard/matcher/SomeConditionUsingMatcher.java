package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.expr.Expression;

import org.bugby.wildcard.WildcardNodeMatcherFromExample;

public class SomeConditionUsingMatcher implements WildcardNodeMatcherFromExample<Expression> {

	@Override
	public boolean matches(Expression node) {
		// check if type = boolean
		return true;
	}

	@Override
	public void init(Node nodeFromExample) {
		// TODO Auto-generated method stub

	}

}

package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.expr.Expression;

import org.bugby.wildcard.WildcardNodeMatcherFromExample;

public class SomeExpressionUsingMatcher implements WildcardNodeMatcherFromExample {

	@Override
	public boolean matches(Node node) {
		return node instanceof Expression;
	}

	@Override
	public void init(Node nodeFromExample) {
		// TODO Auto-generated method stub

	}

}

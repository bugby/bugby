package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.expr.Expression;

import org.bugby.wildcard.api.WildcardNodeMatcher;

public class SomeValueMatcher implements WildcardNodeMatcher {

	@Override
	public boolean matches(Node node) {
		return node instanceof Expression;
	}

}

package org.bugby.wildcard.matcher;

import japa.parser.ast.body.VariableDeclaratorId;

import org.bugby.wildcard.api.WildcardNodeMatcher;

public class SomeVariableMatcher implements WildcardNodeMatcher<VariableDeclaratorId> {

	@Override
	public boolean matches(VariableDeclaratorId node) {
		return true;
	}

}

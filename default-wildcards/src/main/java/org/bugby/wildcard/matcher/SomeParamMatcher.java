package org.bugby.wildcard.matcher;

import japa.parser.ast.body.Parameter;

import org.bugby.wildcard.api.WildcardNodeMatcher;

public class SomeParamMatcher implements WildcardNodeMatcher<Parameter> {

	@Override
	public boolean matches(Parameter node) {
		return true;
	}

}

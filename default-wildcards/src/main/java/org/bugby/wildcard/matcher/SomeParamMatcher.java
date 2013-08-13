package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.body.Parameter;

import org.bugby.wildcard.api.WildcardNodeMatcher;

public class SomeParamMatcher implements WildcardNodeMatcher {

	@Override
	public boolean matches(Node node) {
		return node instanceof Parameter;
	}

}

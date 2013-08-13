package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.body.FieldDeclaration;

import org.bugby.wildcard.api.WildcardNodeMatcher;

public class SomeFieldMatcher implements WildcardNodeMatcher {

	@Override
	public boolean matches(Node node) {
		return node instanceof FieldDeclaration;
	}

}

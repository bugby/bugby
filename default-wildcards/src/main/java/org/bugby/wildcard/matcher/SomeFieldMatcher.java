package org.bugby.wildcard.matcher;

import japa.parser.ast.body.FieldDeclaration;

import org.bugby.wildcard.api.WildcardNodeMatcher;

public class SomeFieldMatcher implements WildcardNodeMatcher<FieldDeclaration> {

	@Override
	public boolean matches(FieldDeclaration node) {
		return true;
	}

}

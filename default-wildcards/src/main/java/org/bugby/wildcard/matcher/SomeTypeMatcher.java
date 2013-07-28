package org.bugby.wildcard.matcher;

import japa.parser.ast.type.ClassOrInterfaceType;

import org.bugby.wildcard.api.WildcardNodeMatcher;

public class SomeTypeMatcher implements WildcardNodeMatcher<ClassOrInterfaceType> {

	@Override
	public boolean matches(ClassOrInterfaceType node) {
		return true;
	}

}

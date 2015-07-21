package org.bugby.bugs.findbugs;

import org.bugby.wildcard.type.SomeType;

public class TypeWithUnsupportedMethod {

	public void unsupportedMethod(SomeType someParameter) {
		// here i should signal that all exception constructors should match
		throw new UnsupportedOperationException();
	}
}

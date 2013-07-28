package org.bugby.bugs.findbugs;

import static org.bugby.wildcard.Wildcards.someTypedValue;
import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.annotation.BadExample;
import org.bugby.wildcard.SomeType;

@BadExample
public class CallToUnsupportedMethod {

	public void someCode() {
		someTypedValue(TypeWithUnsupportedMethod.class).unsupportedMethod((SomeType) someValue());
	}
}

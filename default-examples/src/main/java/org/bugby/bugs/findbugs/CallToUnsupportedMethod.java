package org.bugby.bugs.findbugs;

import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.annotation.BadExample;
import org.bugby.wildcard.SomeType;

@BadExample
public class CallToUnsupportedMethod {
	public void someCode() {
		// t can also be field, variable, parameter etc .... so the initialization itself is not part of the match, only
		// the type
		someValue(TypeWithUnsupportedMethod.class).unsupportedMethod((SomeType) someValue());
	}
}

package org.bugby.bugs.findbugs;

import static org.bugby.wildcard.Wildcards.someTypedValue;
import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.api.annotation.Pattern;
import org.bugby.wildcard.type.SomeType;

@Pattern
public class CallToUnsupportedMethod {

	public void someCode() {
		someTypedValue(TypeWithUnsupportedMethod.class).unsupportedMethod((SomeType) someValue());
	}
}

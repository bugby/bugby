package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;
import static org.bugby.wildcard.Wildcards.someTypeExcepting;
import static org.bugby.wildcard.Wildcards.someTypedValue;

import java.util.Collection;

import org.bugby.wildcard.type.SomeType;

public class UncheckedTypeInGenericCall {
	public void someCode(Collection<SomeType> c) {
		someExpressionUsing(c.contains(someTypedValue(someTypeExcepting(SomeType.class))));
	}
}

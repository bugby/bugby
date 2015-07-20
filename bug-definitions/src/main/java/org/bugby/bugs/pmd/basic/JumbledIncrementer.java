package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;
import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.api.annotation.Pattern;

@Pattern
public class JumbledIncrementer {
	public void someCode() {
		for (Object someVar1 = someValue();;) { // only references 'i'
			for (Object someVar2 = someValue();; someExpressionUsing(someVar1)) { // references both 'i' and 'k'
			}
		}
	}
}

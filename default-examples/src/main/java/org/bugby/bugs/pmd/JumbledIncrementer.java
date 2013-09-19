package org.bugby.bugs.pmd;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;
import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.annotation.BadExample;

@BadExample
public class JumbledIncrementer {
	public void someCode() {
		for (int someVar1 = someValue();;) { // only references 'i'
			for (int someVar2 = someValue();; someExpressionUsing(someVar1)) { // references both 'i' and 'k'
			}
		}
	}
}

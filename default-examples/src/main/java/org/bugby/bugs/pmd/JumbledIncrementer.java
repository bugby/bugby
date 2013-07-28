package org.bugby.bugs.pmd;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;
import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.annotation.BadExample;

@BadExample
public class JumbledIncrementer {
	public void someCode() {
		for (int i = someValue();;) { // only references 'i'
			for (int k = someValue();; someExpressionUsing(i)) { // references both 'i' and 'k'
			}
		}
	}
}

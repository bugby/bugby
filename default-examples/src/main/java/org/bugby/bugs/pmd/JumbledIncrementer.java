package org.bugby.bugs.pmd;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;
import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.annotation.BadExample;

@BadExample
public class JumbledIncrementer {
	public void someCode() {
		// TODO i should find a way to tell i and k are wildcards as well
		for (int i = someValue();;) { // only references 'i'
			for (int k = someValue();; someExpressionUsing(i)) { // references both 'i' and 'k'
			}
		}
	}
}

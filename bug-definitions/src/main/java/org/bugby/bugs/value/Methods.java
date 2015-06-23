package org.bugby.bugs.value;

import org.bugby.annotation.BadExample;
import org.bugby.wildcard.MatchCount;

@BadExample
public class Methods {
	@MatchCount(min = 0, max = 2)
	public void someMethod() {
	}
}

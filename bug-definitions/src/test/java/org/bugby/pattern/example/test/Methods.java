package org.bugby.pattern.example.test;

import org.bugby.api.Pattern;
import org.bugby.wildcard.MatchCount;

@Pattern
public class Methods {
	@MatchCount(min = 0, max = 2)
	public void someMethod() {
	}
}

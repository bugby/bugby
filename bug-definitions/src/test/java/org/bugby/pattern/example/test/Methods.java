package org.bugby.pattern.example.test;

import org.bugby.wildcard.MatchCount;
import org.bugby.wildcard.Pattern;

@Pattern
public class Methods {
	@MatchCount(min = 0, max = 2)
	public void someMethod() {
	}
}

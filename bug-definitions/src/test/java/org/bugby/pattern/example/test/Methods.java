package org.bugby.pattern.example.test;

import org.bugby.api.annotation.MatchCount;
import org.bugby.api.annotation.Pattern;

@Pattern
public class Methods {
	@MatchCount(min = 0, max = 2)
	public void someMethod() {
	}
}

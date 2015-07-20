package org.bugby.pattern.example.test.annotations;

import org.bugby.api.OrSet;
import org.bugby.wildcard.Pattern;

@Pattern
public class OrSetPattern {
	@OrSet(1)
	public void methodBoolean(boolean n) {
	}

	@OrSet(1)
	public void methodDouble(double n) {
	}
}

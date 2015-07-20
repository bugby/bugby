package org.bugby.pattern.example.test.annotations;

import org.bugby.api.annotation.OrSet;
import org.bugby.api.annotation.Pattern;

@Pattern
public class OrSetPattern {
	@OrSet(1)
	public void methodBoolean(boolean n) {
	}

	@OrSet(1)
	public void methodDouble(double n) {
	}
}

package org.bugby.pattern.example.test.var;

import org.bugby.wildcard.Pattern;

@Pattern
public class VariableAssignField {
	int someField1;
	int someField2;

	public void someCode() {
		someField2 = someField1;
	}
}

package org.bugby.pattern.example.test.var;

import org.bugby.api.annotation.Pattern;

@Pattern
public class VariableAssignParam {

	public void someMethod(int someParam1, int someParam2) {
		someParam2 = someParam1;
		someParam2 = 1;
	}
}

package org.bugby.pattern.example.test.var;

import org.bugby.api.annotation.Pattern;

@Pattern
public class VariableAssign {
	public void someCode(int someVar) {
		if (someVar == 2) {
			return;
		}
	}

}

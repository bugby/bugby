package org.bugby.pattern.example.test;

import org.bugby.annotation.BadExample;

@BadExample
public class VariableAssign {
	public void someCode(int someVar) {
		if (someVar == 2) {
			return;
		}
	}

}

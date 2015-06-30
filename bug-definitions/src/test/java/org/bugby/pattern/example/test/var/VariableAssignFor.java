package org.bugby.pattern.example.test.var;

import org.bugby.annotation.BadExample;

@BadExample
public class VariableAssignFor {
	public void someCode() {
		for (int someVar = 0;;) {
			System.out.println(someVar);
		}
	}
}

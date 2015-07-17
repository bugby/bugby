package org.bugby.pattern.example.test.var;

import org.bugby.wildcard.Pattern;

@Pattern
public class VariableAssignFor {
	public void someCode() {
		for (int someVar = 0;;) {
			System.out.println(someVar);
		}
	}
}

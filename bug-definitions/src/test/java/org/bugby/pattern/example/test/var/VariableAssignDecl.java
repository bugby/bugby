package org.bugby.pattern.example.test.var;

import org.bugby.annotation.BadExample;

@BadExample
public class VariableAssignDecl {
	public void someMethod() {
		int someVar1 = 0;
		int someVar2;
		someVar2 = someVar1;
	}
}

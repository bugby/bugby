package org.bugby.pattern.example.test.var;

import org.bugby.wildcard.Pattern;

@Pattern
public class VariableAssignDecl {
	public void someMethod() {
		int someVar1 = 0;
		int someVar2;
		someVar2 = someVar1;
	}
}

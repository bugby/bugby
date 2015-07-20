package org.bugby.pattern.example.test.var;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;

import org.bugby.api.annotation.Pattern;

@Pattern
public class MultipleVariableAssignSomeCode {
	public void someCode(int someVar1, int someVar2) {
		someExpressionUsing(someVar1);
		someVar2 = someVar1;
	}
}

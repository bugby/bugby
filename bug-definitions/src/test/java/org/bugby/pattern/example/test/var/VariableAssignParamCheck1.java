package org.bugby.pattern.example.test.var;

import org.bugby.annotation.BadExample;

@BadExample
public class VariableAssignParamCheck1 {

	public void myMethod(int x, int y, int z) {
		z = x;
		z = 1;
	}
}

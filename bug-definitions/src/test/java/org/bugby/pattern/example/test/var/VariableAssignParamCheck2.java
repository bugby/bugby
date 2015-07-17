package org.bugby.pattern.example.test.var;

import org.bugby.wildcard.Pattern;

@Pattern
public class VariableAssignParamCheck2 {

	public void myMethod(int x, int y, int z) {
		z = x;
		x = 1;
	}
}

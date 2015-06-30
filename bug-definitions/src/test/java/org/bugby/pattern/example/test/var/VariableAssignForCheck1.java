package org.bugby.pattern.example.test.var;

public class VariableAssignForCheck1 {
	public void myMethod() {
		for (int x = 0; x < 10; ++x) {
			System.err.println(x);
		}
		for (int y = 0; y < 10; ++y) {
			System.out.println(y);
		}
	}
}

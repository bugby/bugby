package org.bugby.pattern.example.test;

public class CollapsibleIfStatementsCheck {

	public CollapsibleIfStatementsCheck() {
		myMethod();
	}

	public void myMethod() {
		int n = 100;

		if (n < 10) {
			if (n > 2) {
				System.out.println("found a good number");
			}
		}
		System.out.println("finished test");
	}
}

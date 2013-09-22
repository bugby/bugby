package org.bugby.pattern.example.pmd;

public class CollapsibleIfStatementsCheck3 {

	public CollapsibleIfStatementsCheck3() {
		myMethod();
	}

	public void myMethod() {
		int n = 100;

		if (n < 10) {
			System.out.println("additional code");
			if (n > 2) {
				System.out.println("found a good number");
			}
		}
		System.out.println("finished test");
	}
}

package org.bugby.pattern.example.pmd;

public class CollapsibleIfStatementsCheck4 {

	public CollapsibleIfStatementsCheck4() {
		myMethod();
	}

	public void myMethod() {
		int n = 100;

		if (n < 10 && n % 2 != 1) {
			if (n > 2) {
				System.out.println("found a good number");
			}
		}
		System.out.println("finished test");
	}

	public void myMethod2() {
		int n = 30;

		if (n < 5) {
			if (n > 7) {
				System.out.println("found a better number");
			}
		}
		System.out.println("finished test2");
	}

}

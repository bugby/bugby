package org.bugby.pattern.example.pmd.basic;

public class CollapsibleIfStatementsCheck2 {

	public CollapsibleIfStatementsCheck2() {
		myMethod();
	}

	public void myMethod() {
		int n = 100;

		if (n < 10) {
			if (n > 2) {
				System.out.println("found a good number");
			}
			System.out.println("additional code");
		}
		System.out.println("finished test");
	}
}

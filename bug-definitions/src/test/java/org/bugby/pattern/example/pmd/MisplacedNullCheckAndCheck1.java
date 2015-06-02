package org.bugby.pattern.example.pmd;

public class MisplacedNullCheckAndCheck1 {
	public void someCode() {
		Integer a = 12;
		Boolean b = false;

		if (a > 10 && (a != null || !b) && b != true) {
			System.out.println("A is good");
		}
	}
}

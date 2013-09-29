package org.bugby.pattern.example.pmd;

public class MisplacedNullCheckOrCheck1 {
	public void someCode() {
		Integer a = 12;

		if (a > 10 || a == null) {
			System.out.println("A is good");
		}
	}
}

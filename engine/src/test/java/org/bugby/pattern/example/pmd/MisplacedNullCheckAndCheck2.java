package org.bugby.pattern.example.pmd;

public class MisplacedNullCheckAndCheck2 {
	public void someCode() {
		Integer a = 12;
		if (a != null && a > 10) {
			System.out.println("A is good");
		}
	}
}

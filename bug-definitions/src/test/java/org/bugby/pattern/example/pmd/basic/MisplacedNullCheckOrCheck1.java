package org.bugby.pattern.example.pmd.basic;

public class MisplacedNullCheckOrCheck1 {
	public void someCode(Integer a) {

		if (a > 10 || a == null) {
			System.out.println("A is good");
		}
	}
}

package org.bugby.pattern.example.pmd.basic;

public class JumbledIncrementerCheck3 {
	public void foo() {
		for (int i = 0; i < 10; i++) { // only references 'i'
			for (int k = 0; k < 20; i++) { // references both 'i' and 'k'
				System.out.println("Hello");
			}
		}
	}
}
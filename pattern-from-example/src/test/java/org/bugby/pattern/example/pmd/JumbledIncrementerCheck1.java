package org.bugby.pattern.example.pmd;

public class JumbledIncrementerCheck1 {
	public void someCode() {
		for (int i = 0; i < 10; ++i) { // only references 'i'
			for (int j = 2; j < 10; j = i + 2) { // references both 'i' and 'k'
				System.out.println("Sum:" + i + j);
			}
		}
	}
}
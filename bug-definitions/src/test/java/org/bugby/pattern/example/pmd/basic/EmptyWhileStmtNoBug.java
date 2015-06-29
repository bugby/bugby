package org.bugby.pattern.example.pmd.basic;

public class EmptyWhileStmtNoBug {
	void bar(int a, int b) {
		while (a == b) {
			System.out.println("doing stuff");
		}
	}
}

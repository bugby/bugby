package org.bugby.pattern.example.pmd.basic;

public class EmptyWhileStmtBug {
	void bar(int a, int b) {
		while (a == b) {
			// empty!
		}
	}
}

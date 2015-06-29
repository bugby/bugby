package org.bugby.pattern.example.pmd.basic;


public class EmptyIfStmtBug {
	void bar(int x) {
		if (x == 0) {
			// empty!
		}
	}
}

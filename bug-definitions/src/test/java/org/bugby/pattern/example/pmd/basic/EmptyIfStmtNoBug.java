package org.bugby.pattern.example.pmd.basic;


public class EmptyIfStmtNoBug {

	void bar(int x) {
		if (x == 0) {
			System.out.println("x is zero");
		}
	}
}

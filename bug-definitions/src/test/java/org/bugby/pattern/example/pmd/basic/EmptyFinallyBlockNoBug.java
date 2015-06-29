package org.bugby.pattern.example.pmd.basic;

public class EmptyFinallyBlockNoBug {
	public void bar() {
		try {
			int x = 2;
		}
		finally {
			System.out.println("finally");
		}
	}
}

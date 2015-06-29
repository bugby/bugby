package org.bugby.pattern.example.pmd.basic;

public class EmptyTryBlockNoBug {
	public void bar() {
		try {
			System.out.println("What's up");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package org.bugby.pattern.example.pmd.basic;

public class EmptyTryBlockBug {
	public void bar() {
		try {
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
}

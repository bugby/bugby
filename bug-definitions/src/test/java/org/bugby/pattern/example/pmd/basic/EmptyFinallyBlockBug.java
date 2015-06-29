package org.bugby.pattern.example.pmd.basic;

public class EmptyFinallyBlockBug {
	public void bar() {
		try {
			int x = 2;
		}
		finally {
			// empty!
		}
	}
}

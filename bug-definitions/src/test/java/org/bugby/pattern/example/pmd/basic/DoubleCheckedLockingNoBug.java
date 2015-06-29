package org.bugby.pattern.example.pmd.basic;

public class DoubleCheckedLockingNoBug {
	Object baz;

	Object bar() {
		if (baz == null) { //baz may be non-null yet not fully created
			if (baz == null) {
				baz = new Object();
			}
		}
		return baz;
	}
}

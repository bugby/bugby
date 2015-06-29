package org.bugby.pattern.example.pmd.basic;

public class DoubleCheckedLockingBug {
	Object baz;

	Object bar() {
		if (baz == null) { //baz may be non-null yet not fully created
			synchronized (this) {
				if (baz == null) {
					baz = new Object();
				}
			}
		}
		return baz;
	}
}

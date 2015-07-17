package org.bugby.pattern.example.findbugs.badpractice;

public class CloneNoSuperCallNoBug implements Cloneable {
	@Override
	public Object clone() {
		try {
			return super.clone();
		}
		catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}

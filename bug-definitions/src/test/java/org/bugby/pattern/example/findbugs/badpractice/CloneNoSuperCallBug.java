package org.bugby.pattern.example.findbugs.badpractice;

public class CloneNoSuperCallBug implements Cloneable {
	@Override
	public Object clone() {
		try {
			return new CloneNoSuperCallBug();
		}
		catch (RuntimeException ex) {
			return null;
		}
	}
}

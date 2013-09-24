package org.bugby.pattern.example.findbugs;

public class CloneableWithoutCloneCheck1 implements Cloneable {
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

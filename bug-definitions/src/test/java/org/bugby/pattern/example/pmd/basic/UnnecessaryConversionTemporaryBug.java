package org.bugby.pattern.example.pmd.basic;

public class UnnecessaryConversionTemporaryBug {
	public String convert(int x) {
		// this wastes an object
		String foo = new Integer(x).toString();
		return foo;
	}
}

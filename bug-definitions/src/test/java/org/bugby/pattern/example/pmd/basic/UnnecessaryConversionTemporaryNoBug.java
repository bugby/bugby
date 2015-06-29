package org.bugby.pattern.example.pmd.basic;

public class UnnecessaryConversionTemporaryNoBug {
	public String convert(int x) {
		return Integer.toString(x);
	}
}

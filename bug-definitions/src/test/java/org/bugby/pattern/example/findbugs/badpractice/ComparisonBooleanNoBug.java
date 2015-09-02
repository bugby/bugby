package org.bugby.pattern.example.findbugs.badpractice;

public class ComparisonBooleanNoBug {
	public boolean field;

	public boolean check(boolean param) {
		if (field == param) {
			return true;
		}
		return false;
	}
}

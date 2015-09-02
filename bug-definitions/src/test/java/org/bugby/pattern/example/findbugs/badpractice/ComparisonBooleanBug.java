package org.bugby.pattern.example.findbugs.badpractice;

public class ComparisonBooleanBug {
	public Boolean field;

	public boolean check(Boolean param) {
		if (field == param) {
			return true;
		}
		return false;
	}

}

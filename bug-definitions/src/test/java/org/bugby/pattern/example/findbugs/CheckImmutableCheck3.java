package org.bugby.pattern.example.findbugs;

public class CheckImmutableCheck3 {
	private int count;

	/**
	 * i need this constructor only for eclipse to allow the final field. this is not part of the matching
	 */
	public CheckImmutableCheck3() {
		this.count = 0;
	}

	public int getCount() {
		return count;
	}

}

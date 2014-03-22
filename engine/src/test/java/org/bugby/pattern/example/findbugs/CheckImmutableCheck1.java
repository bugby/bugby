package org.bugby.pattern.example.findbugs;

import javax.annotation.concurrent.Immutable;

@Immutable
public class CheckImmutableCheck1 {
	private final int count;

	/**
	 * i need this constructor only for eclipse to allow the final field. this is not part of the matching
	 */
	public CheckImmutableCheck1() {
		this.count = 0;
	}

	public int getCount() {
		return count;
	}

}

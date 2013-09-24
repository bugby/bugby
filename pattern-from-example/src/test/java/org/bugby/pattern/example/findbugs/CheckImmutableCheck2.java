package org.bugby.pattern.example.findbugs;

import javax.annotation.concurrent.Immutable;

@Immutable
public class CheckImmutableCheck2 {
	private int field;

	public int getField() {
		return field;
	}

	public void setField(int field) {
		this.field = field;
	}

}

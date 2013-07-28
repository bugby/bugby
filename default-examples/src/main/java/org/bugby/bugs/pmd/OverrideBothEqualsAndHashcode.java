package org.bugby.bugs.pmd;

import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.annotation.GoodExample;

@GoodExample
public class OverrideBothEqualsAndHashcode {
	@Override
	public boolean equals(Object someParam) {
		return someValue();
	}

	@Override
	public int hashCode() {
		return someValue();
	}
}

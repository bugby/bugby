package org.bugby.bugs.pmd;

import org.bugby.annotation.GoodExample;
import org.bugby.wildcard.Wildcards;

@GoodExample
public class OverrideBothEqualsAndHashcode {
	@Override
	public boolean equals(Object someParam) {
		return Wildcards.<Boolean> someValue();
	}

	@Override
	public int hashCode() {
		return Wildcards.<Integer> someValue();
	}
}

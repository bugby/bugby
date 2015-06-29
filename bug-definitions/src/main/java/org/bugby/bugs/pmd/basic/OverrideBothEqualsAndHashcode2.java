package org.bugby.bugs.pmd.basic;

import org.bugby.annotation.BadExample;
import org.bugby.wildcard.Missing;
import org.bugby.wildcard.Wildcards;

@BadExample
public class OverrideBothEqualsAndHashcode2 {
	@Override
	public boolean equals(Object someParam) {
		return Wildcards.<Boolean> someValue();
	}

	@Override
	@Missing
	public int hashCode() {
		return Wildcards.<Integer> someValue();
	}
}

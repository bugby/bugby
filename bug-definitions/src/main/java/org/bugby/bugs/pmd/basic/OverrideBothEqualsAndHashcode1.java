package org.bugby.bugs.pmd.basic;

import org.bugby.api.annotation.Missing;
import org.bugby.api.annotation.Pattern;
import org.bugby.wildcard.Wildcards;

@Pattern
public class OverrideBothEqualsAndHashcode1 {
	@Override
	@Missing
	public boolean equals(Object someParam) {
		return Wildcards.<Boolean> someValue();
	}

	@Override
	public int hashCode() {
		return Wildcards.<Integer> someValue();
	}
}

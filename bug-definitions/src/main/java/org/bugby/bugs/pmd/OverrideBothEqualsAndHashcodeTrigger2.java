package org.bugby.bugs.pmd;

import org.bugby.annotation.GoodExampleTrigger;
import org.bugby.wildcard.Wildcards;

@GoodExampleTrigger(forExample = OverrideBothEqualsAndHashcode.class)
public class OverrideBothEqualsAndHashcodeTrigger2 {
	@Override
	public boolean equals(Object someParam) {
		return Wildcards.<Boolean> someValue();
	}

}

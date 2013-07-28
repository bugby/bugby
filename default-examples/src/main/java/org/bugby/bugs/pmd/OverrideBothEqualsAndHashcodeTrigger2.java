package org.bugby.bugs.pmd;

import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.annotation.GoodExampleTrigger;

@GoodExampleTrigger(
		forExample = OverrideBothEqualsAndHashcode.class)
public class OverrideBothEqualsAndHashcodeTrigger2 {
	@Override
	public boolean equals(Object someParam) {
		return someValue();
	}

}

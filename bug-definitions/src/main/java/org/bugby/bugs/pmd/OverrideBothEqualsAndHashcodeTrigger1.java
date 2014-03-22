package org.bugby.bugs.pmd;

import org.bugby.annotation.GoodExampleTrigger;
import org.bugby.wildcard.Wildcards;

@GoodExampleTrigger(forExample = OverrideBothEqualsAndHashcode.class)
public class OverrideBothEqualsAndHashcodeTrigger1 {

	@Override
	public int hashCode() {
		return Wildcards.<Integer> someValue();
	}
}

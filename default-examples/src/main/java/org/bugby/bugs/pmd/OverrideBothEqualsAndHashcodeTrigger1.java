package org.bugby.bugs.pmd;

import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.annotation.GoodExampleTrigger;

@GoodExampleTrigger(
		forExample = OverrideBothEqualsAndHashcode.class)
public class OverrideBothEqualsAndHashcodeTrigger1 {

	@Override
	public int hashCode() {
		return someValue();
	}
}

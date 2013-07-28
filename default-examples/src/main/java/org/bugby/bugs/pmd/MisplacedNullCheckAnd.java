package org.bugby.bugs.pmd;

import static org.bugby.wildcard.Wildcards.someConditionUsing;
import static org.bugby.wildcard.Wildcards.someTypedValue;

import org.bugby.annotation.BadExample;
import org.bugby.annotation.IgnoreFromMatching;
import org.bugby.wildcard.SomeType;

@BadExample
public class MisplacedNullCheckAnd {
	public void someCode() {
		@IgnoreFromMatching
		SomeType a = someTypedValue(SomeType.class);

		if (someConditionUsing(a) && a != null) {

		}
	}
}

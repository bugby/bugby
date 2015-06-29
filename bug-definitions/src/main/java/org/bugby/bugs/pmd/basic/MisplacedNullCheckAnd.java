package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.someConditionUsing;

import org.bugby.annotation.BadExample;
import org.bugby.wildcard.Wildcards;

@BadExample
public class MisplacedNullCheckAnd {
	public void someCode(Object someVar) {

		if (Wildcards.<Boolean> anywhere(someConditionUsing(someVar) && someConditionUsing(someVar != null))) {
			//
		}
	}
}

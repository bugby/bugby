package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.anywhere;
import static org.bugby.wildcard.Wildcards.someConditionUsing;

import org.bugby.annotation.BadExample;

@BadExample
public class MisplacedNullCheckOr {
	public void someCode(Object someVar) {

		if (anywhere(someConditionUsing(someVar) || someConditionUsing(someVar == null))) {
			//
		}
	}
}

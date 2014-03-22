package org.bugby.bugs.pmd;

import static org.bugby.wildcard.Wildcards.someConditionUsing;

import org.bugby.annotation.BadExample;

@BadExample
public class MisplacedNullCheckOr {
	public void someCode(Object someVar) {

		if (someConditionUsing(someVar) || someVar == null) {

		}
	}
}

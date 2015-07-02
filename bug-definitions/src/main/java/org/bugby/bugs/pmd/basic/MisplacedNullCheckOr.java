package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.someConditionUsing;

import org.bugby.api.Pattern;
import org.bugby.wildcard.Wildcards;

@Pattern
public class MisplacedNullCheckOr {
	public void someCode(Object someVar) {

		if (Wildcards.<Boolean> anywhere(someConditionUsing(someVar) || someConditionUsing(someVar == null))) {
			//
		}
	}
}

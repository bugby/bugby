package org.bugby.bugs.pmd;

import static org.bugby.wildcard.Wildcards.anywhere;
import static org.bugby.wildcard.Wildcards.someConditionUsing;

import org.bugby.annotation.BadExample;

@BadExample
public class MisplacedNullCheckAnd {
	public void someCode(Object someVar) {

		if (anywhere(someConditionUsing(someVar) && someConditionUsing(someVar != null))) {

		}
	}
}

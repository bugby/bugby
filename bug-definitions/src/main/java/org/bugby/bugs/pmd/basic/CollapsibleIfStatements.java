package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.begin;
import static org.bugby.wildcard.Wildcards.end;
import static org.bugby.wildcard.Wildcards.someCondition;

import org.bugby.api.annotation.Pattern;

@Pattern
public class CollapsibleIfStatements {

	public void someCode() {
		// TODO i need to check that the condition have no "else"
		if (someCondition()) {
			// i need to check the this block contains ONLY the if statement
			begin();
			if (someCondition()) {

			}
			end();
		}
	}
}

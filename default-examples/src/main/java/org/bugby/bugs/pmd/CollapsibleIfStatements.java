package org.bugby.bugs.pmd;

import static org.bugby.wildcard.Wildcards.someCondition;

import org.bugby.annotation.BadExample;

@BadExample
public class CollapsibleIfStatements {

	public void someCode() {
		// TODO i need to check that the condition have no "else"
		if (someCondition()) {
			// TODO i need to check the this block contains ONLY the if statement
			if (someCondition()) {

			}
		}
	}
}

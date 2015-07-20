package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.noCode;
import static org.bugby.wildcard.Wildcards.someCondition;

import org.bugby.api.annotation.Pattern;

@Pattern
public class EmptyWhileStmt {
	public void someCode() {
		while (someCondition()) {
			noCode();
		}
	}
}

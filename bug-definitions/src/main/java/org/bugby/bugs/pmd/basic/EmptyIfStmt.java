package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.noCode;
import static org.bugby.wildcard.Wildcards.someCondition;

import org.bugby.wildcard.Pattern;

@Pattern
public class EmptyIfStmt {
	public void someCode() {
		if (someCondition()) {
			noCode();
		}
	}
}

package org.bugby.bugs.pmd;

import static org.bugby.wildcard.Wildcards.anyBranch;
import static org.bugby.wildcard.Wildcards.someCondition;

import org.bugby.annotation.GoodExample;

@GoodExample
public class AvoidBranchingStatementAsLastInLoop {
	public void someCode() {
		// I need all combinations of while,for,do and break, continue, return
		// $AnyLoop();
		for (;;) {
			if (someCondition()) {
				anyBranch();
			}
		}
	}
}

package org.bugby.bugs.pmd;

import org.bugby.annotation.BadExample;

@BadExample
public class AvoidBranchingStatementAsLastInLoop {
	public void someCode() {
		for (;;) {
			// TODO - this should be the last code line
			break;
		}
	}
}

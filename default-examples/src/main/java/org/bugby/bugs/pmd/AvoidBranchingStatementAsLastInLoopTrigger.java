package org.bugby.bugs.pmd;

import static org.bugby.wildcard.Wildcards.anyBranch;

import org.bugby.annotation.GoodExampleTrigger;

@GoodExampleTrigger(forExample = AvoidBranchingStatementAsLastInLoop.class)
public class AvoidBranchingStatementAsLastInLoopTrigger {
	public void someCode() {
		// $AnyLoop();
		for (;;) {
			anyBranch();
		}
	}
}

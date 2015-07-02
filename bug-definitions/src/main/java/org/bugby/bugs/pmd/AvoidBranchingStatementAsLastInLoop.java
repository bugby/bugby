package org.bugby.bugs.pmd;

import static org.bugby.wildcard.Wildcards.anyBranch;
import static org.bugby.wildcard.Wildcards.end;

import org.bugby.api.Pattern;

@Pattern
public class AvoidBranchingStatementAsLastInLoop {
	public void someCode() {
		// I need all combinations of while,for,do and break, continue, return
		// $AnyLoop();
		for (;;) {
			end();
			anyBranch();
		}
	}
}

package org.bugby.bugs.pmd;

import static org.bugby.wildcard.WildcardAnnotations.$AnyLoop;
import static org.bugby.wildcard.WildcardAnnotations.$Last;

import org.bugby.annotation.BadExample;

@BadExample
public class AvoidBranchingStatementAsLastInLoop {
	public void someCode() {
		$AnyLoop();
		for (;;) {
			// TODO - this should be the last code line
			$Last();
			break;
		}
	}
}

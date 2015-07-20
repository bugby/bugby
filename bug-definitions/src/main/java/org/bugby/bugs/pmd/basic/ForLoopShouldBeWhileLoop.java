package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.noCode;
import static org.bugby.wildcard.Wildcards.someBooleanValue;

import org.bugby.api.annotation.Pattern;

@Pattern
public class ForLoopShouldBeWhileLoop {
	public void someCode() {
		for (noCode(); someBooleanValue(); noCode()) {
		}
	}
}

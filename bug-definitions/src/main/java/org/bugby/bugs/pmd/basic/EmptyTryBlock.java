package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.noCode;

import org.bugby.wildcard.Pattern;

@Pattern
public class EmptyTryBlock {
	public void someCode() {

		try {
			noCode();
		}
		catch (RuntimeException someVar) {

		}
	}
}

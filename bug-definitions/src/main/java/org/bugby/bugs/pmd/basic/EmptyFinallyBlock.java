package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.noCode;

import org.bugby.wildcard.Pattern;

@Pattern
public class EmptyFinallyBlock {
	public void someCode() {

		try {

		}
		finally {
			noCode();
		}
	}
}

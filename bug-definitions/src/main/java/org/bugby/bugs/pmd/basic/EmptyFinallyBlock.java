package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.noCode;

import org.bugby.annotation.BadExample;

@BadExample
public class EmptyFinallyBlock {
	public void someCode() {

		try {

		}
		finally {
			noCode();
		}
	}
}

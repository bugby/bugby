package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.noCode;

import org.bugby.annotation.BadExample;
import org.bugby.wildcard.SomeRuntimeException;

@BadExample
public class EmptyTryBlock {
	public void someCode() {

		try {
			noCode();
		}
		catch (SomeRuntimeException someVar) {

		}
	}
}

package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.noCode;

import org.bugby.api.Pattern;
import org.bugby.wildcard.SomeRuntimeException;

@Pattern
public class EmptyTryBlock {
	public void someCode() {

		try {
			noCode();
		}
		catch (SomeRuntimeException someVar) {

		}
	}
}

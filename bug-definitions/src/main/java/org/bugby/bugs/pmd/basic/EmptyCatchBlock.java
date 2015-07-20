package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.noCode;
import static org.bugby.wildcard.Wildcards.someExpressionThrowing;

import org.bugby.api.annotation.Pattern;
import org.bugby.wildcard.SomeException;

@Pattern
public class EmptyCatchBlock {
	public void someCode() {
		try {
			someExpressionThrowing(SomeException.class);
		}
		catch (SomeException someVar) {
			noCode();
		}
	}
}

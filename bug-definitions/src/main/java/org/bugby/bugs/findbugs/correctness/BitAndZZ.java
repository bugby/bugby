package org.bugby.bugs.findbugs.correctness;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;
import static org.bugby.wildcard.Wildcards.someIntValue;

/**
 *
 * BIT: Check to see if ((...) & 0) == 0 (BIT_AND_ZZ) This method compares an expression of the form (e & 0) to 0, which will always compare
 * equal. This may indicate a logic error or typo.
 * @author acraciun
 */
public class BitAndZZ {
	public void someCode() {
		someExpressionUsing((someIntValue() & 0) == 0);
	}
}

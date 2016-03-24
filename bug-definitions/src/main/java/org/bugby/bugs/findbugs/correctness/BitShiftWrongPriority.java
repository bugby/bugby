package org.bugby.bugs.findbugs.correctness;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;
import static org.bugby.wildcard.Wildcards.someIntValue;

/**
 *
 * BSHIFT: Possible bad parsing of shift operation (BSHIFT_WRONG_ADD_PRIORITY) The code performs an operation like (x << 8 + y). Although this
 * might be correct, probably it was meant to perform (x << 8) + y, but shift operation has a lower precedence, so it's actually parsed as x <<
 * (8 + y).
 * @author acraciun
 */
public class BitShiftWrongPriority {
	public void someCode() {
		someExpressionUsing(someIntValue() << someIntValue() + someIntValue());
	}
}

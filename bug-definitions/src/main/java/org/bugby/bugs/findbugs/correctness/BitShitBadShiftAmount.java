package org.bugby.bugs.findbugs.correctness;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;
import static org.bugby.wildcard.Wildcards.someIntValue;

public class BitShitBadShiftAmount {
	public void someClass() {
		someExpressionUsing(someIntValue() << someIntValue(-31, 31)); //some constant ?
	}
}
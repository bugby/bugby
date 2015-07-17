package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;
import static org.bugby.wildcard.Wildcards.someFloatValue;
import static org.bugby.wildcard.Wildcards.someIntValue;

import org.bugby.wildcard.Pattern;

/**
 * Co: compareTo()/compare() incorrectly handles float or double value (CO_COMPARETO_INCORRECT_FLOATING) This method compares double or float
 * values using pattern like this: val1 > val2 ? 1 : val1 < val2 ? -1 : 0. This pattern works incorrectly for -0.0 and NaN values which may
 * result in incorrect sorting result or broken collection (if compared values are used as keys). Consider using Double.compare or Float.compare
 * static methods which handle all the special cases correctly. @author acraciun
 */
@Pattern
public class CompareToIncorrectFloating implements Comparable<Object> {
	@Override
	public int compareTo(Object obj) {
		someExpressionUsing(someFloatValue() > someFloatValue());
		//TODO same for someExpressionUsing(someFloatValue() < someFloatValue());
		return someIntValue();
	}
}

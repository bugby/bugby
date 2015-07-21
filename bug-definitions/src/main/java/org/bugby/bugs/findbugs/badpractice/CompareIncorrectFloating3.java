package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someDoubleValue;
import static org.bugby.wildcard.Wildcards.someExpressionUsing;
import static org.bugby.wildcard.Wildcards.someIntValue;

import java.util.Comparator;

import org.bugby.api.annotation.Pattern;

/**
 * Co: compareTo()/compare() incorrectly handles float or double value (CO_COMPARETO_INCORRECT_FLOATING) This method compares double or float
 * values using pattern like this: val1 > val2 ? 1 : val1 < val2 ? -1 : 0. This pattern works incorrectly for -0.0 and NaN values which may
 * result in incorrect sorting result or broken collection (if compared values are used as keys). Consider using Double.compare or Float.compare
 * static methods which handle all the special cases correctly. @author acraciun
 */
@Pattern
public class CompareIncorrectFloating3 implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		someExpressionUsing(someDoubleValue() > someDoubleValue());
		return someIntValue();
	}
}

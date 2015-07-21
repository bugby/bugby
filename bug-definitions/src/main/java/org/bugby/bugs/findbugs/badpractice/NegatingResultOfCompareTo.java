package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someTypedValue;
import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.api.annotation.Pattern;

/**
 *
 * RV: Negating the result of compareTo()/compare() (RV_NEGATING_RESULT_OF_COMPARETO) This code negatives the return value of a compareTo or
 * compare method. This is a questionable or bad programming practice, since if the return value is Integer.MIN_VALUE, negating the return value
 * won't negate the sign of the result. You can achieve the same intended result by reversing the order of the operands rather than by negating
 * the results.
 * 
 * @author acraciun
 */
@Pattern
public class NegatingResultOfCompareTo {
	@SuppressWarnings("unchecked")
	public int someMethod() {
		return -someTypedValue(Comparable.class).compareTo(someValue());
	}
}
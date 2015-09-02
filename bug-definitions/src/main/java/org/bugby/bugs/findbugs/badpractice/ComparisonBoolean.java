package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;
import static org.bugby.wildcard.Wildcards.someTypedValue;

import org.bugby.api.annotation.OrSet;
import org.bugby.api.annotation.Pattern;

/**
 *
 * RC: Suspicious reference comparison of Boolean values (RC_REF_COMPARISON_BAD_PRACTICE_BOOLEAN) This method compares two Boolean values using
 * the == or != operator. Normally, there are only two Boolean values (Boolean.TRUE and Boolean.FALSE), but it is possible to create other
 * Boolean objects using the new Boolean(b) constructor. It is best to avoid such objects, but if they do exist, then checking Boolean objects
 * for equality using == or != will give results than are different than you would get using .equals(...)
 *
 * @author acraciun
 */
@Pattern
public class ComparisonBoolean {
	@OrSet
	public void someCode1() {
		someExpressionUsing(someTypedValue(Boolean.class) == someTypedValue(Boolean.class));
	}

	@OrSet
	public void someCode2() {
		someExpressionUsing(someTypedValue(Boolean.class) != someTypedValue(Boolean.class));
	}
}

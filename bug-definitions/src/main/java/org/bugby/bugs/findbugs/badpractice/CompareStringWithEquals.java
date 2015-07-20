package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;

import org.bugby.api.annotation.Pattern;

/**
 * ES: Comparison of String parameter using == or != (ES_COMPARING_PARAMETER_STRING_WITH_EQ/ES_COMPARING_STRINGS_WITH_EQ)
 *
 * This code compares a java.lang.String parameter for reference equality using the == or != operators. Requiring callers to pass only String
 * constants or interned strings to a method is unnecessarily fragile, and rarely leads to measurable performance gains. Consider using the
 * equals(Object) method instead.
 *
 *
 * @author acraciun
 */
@Pattern
public class CompareStringWithEquals {
	public void someCode(String x, String y) {
		someExpressionUsing(x == y);
		//TODO same for  x!= y
	}
}

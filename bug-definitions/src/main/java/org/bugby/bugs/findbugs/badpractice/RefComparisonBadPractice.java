package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;
import static org.bugby.wildcard.Wildcards.someTypedValue;

import org.bugby.api.annotation.Pattern;

/**
 *
 * RC: Suspicious reference comparison to constant (RC_REF_COMPARISON_BAD_PRACTICE) This method compares a reference value to a constant using
 * the == or != operator, where the correct way to compare instances of this type is generally with the equals() method. It is possible to create
 * distinct instances that are equal but do not compare as == since they are different objects. Examples of classes which should generally not be
 * compared by reference are java.lang.Integer, java.lang.Float, etc.
 * 
 * @author acraciun
 */
@Pattern
public class RefComparisonBadPractice {
	public void someCode() {
		someExpressionUsing(someTypedValue(Number.class) == someTypedValue(Number.class));
	}

}
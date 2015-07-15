package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someIntValue;

import org.bugby.wildcard.SomeTypeExcepting;

/**
 * CNT: Rough value of known constant found (CNT_ROUGH_CONSTANT_VALUE) It's recommended to use the predefined library constant for code clarity
 * and better precision. ??? Co: Abstract class defines covariant compareTo() method (CO_ABSTRACT_SELF) This class defines a covariant version of
 * compareTo(). To correctly override the compareTo() method in the Comparable interface, the parameter of compareTo() must have type
 * java.lang.Object.
 * @author acraciun
 */
public class CovariantAbstractSelf {
	public int compareTo(SomeTypeExcepting<Object> obj) {
		return someIntValue();
	}
}
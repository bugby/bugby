package org.bugby.bugs.findbugs.badpractice;

import org.bugby.api.Pattern;

/**
 * Co: compareTo()/compare() returns Integer.MIN_VALUE (CO_COMPARETO_RESULTS_MIN_VALUE) In some situation, this compareTo or compare method <br>
 * <br>
 * returns the constant Integer.MIN_VALUE, which is an exceptionally bad practice. The only thing that matters about the return value of
 * compareTo is the sign of the result. But people will sometimes negate the return value of compareTo, expecting that this will negate the sign
 * of the result. And it will, except in the case where the value returned is Integer.MIN_VALUE. So just return -1 rather than Integer.MIN_VALUE.
 * @author acraciun
 */
@Pattern
public class CompareResultMinValue {
	public int compareTo(Object obj) {
		return Integer.MIN_VALUE;
	}
}
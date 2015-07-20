package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;

import org.bugby.api.annotation.Pattern;

/**
 *
 BIT: Check for sign of bitwise operation (BIT_SIGNED_CHECK) This method compares an expression such as ((event.detail & SWT.SELECTED) > 0) .
 * Using bit arithmetic and then comparing with the greater than operator can lead to unexpected results (of course depending on the value of
 * SWT.SELECTED). If SWT.SELECTED is a negative number, this is a candidate for a bug. Even when SWT.SELECTED is not negative, it seems good
 * practice to use '!= 0' instead of '> 0'.
 * @author acraciun
 */
@Pattern
public class BitSignedCheck {
	public void someCode(int n, int mask) {
		someExpressionUsing((n & mask) > 0);
	}
}

package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.WildcardAnnotations.$Missing;
import static org.bugby.wildcard.Wildcards.someBooleanValue;
import static org.bugby.wildcard.Wildcards.someExpressionUsing;

import org.bugby.api.annotation.Pattern;

/**
 * NP: equals() method does not check for null argument (NP_EQUALS_SHOULD_HANDLE_NULL_ARGUMENT) This implementation of equals(Object) violates
 * the contract defined by java.lang.Object.equals() because it does not check for null being passed as the argument. All equals() methods should
 * return false if passed a null value.
 * 
 * @author acraciun
 */
@Pattern
public class EqualsShouldHandleNullArgument {
	@Override
	public boolean equals(Object obj) {
		$Missing();
		someExpressionUsing(obj == null);
		return someBooleanValue();
	}
}
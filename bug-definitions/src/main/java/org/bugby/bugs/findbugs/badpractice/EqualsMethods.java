package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someBooleanValue;
import static org.bugby.wildcard.Wildcards.someExpressionUsing;

import org.bugby.wildcard.Pattern;

/**
 * BC: Equals method should not assume anything about the type of its argument (BC_EQUALS_METHOD_SHOULD_WORK_FOR_ALL_OBJECTS) The equals(Object
 * o) method shouldn't make any assumptions about the type of o. It should simply return false if o is not the same type as this.
 * @author acraciun
 */
@Pattern
public class EqualsMethods {
	@Override
	public boolean equals(Object obj) {
		//TODO @Missing
		someExpressionUsing(obj instanceof EqualsMethods);
		return someBooleanValue();
	}
}

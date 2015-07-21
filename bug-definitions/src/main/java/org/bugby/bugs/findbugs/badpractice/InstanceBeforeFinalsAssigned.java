package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;
import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.api.annotation.ModifiersMatching;
import org.bugby.api.annotation.Pattern;

/**
 *
 * SI: Static initializer creates instance before all static final fields assigned (SI_INSTANCE_BEFORE_FINALS_ASSIGNED) The class's static
 * initializer creates an instance of the class before all of the static final fields are assigned.
 * 
 * @author acraciun
 */
@Pattern
public class InstanceBeforeFinalsAssigned {
	//TODO order of fields and blocks is important! @TypeMathchinh(orderedMembers=true)
	static {
		someExpressionUsing(new InstanceBeforeFinalsAssigned());
	}
	@ModifiersMatching(STATIC = true)
	private static Object otherField = someValue();

}
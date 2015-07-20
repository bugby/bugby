package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someBooleanValue;

import org.bugby.api.annotation.Pattern;
import org.bugby.api.annotation.VariableMatching;

/**
 *
 * Eq: Abstract class defines covariant equals() method (EQ_ABSTRACT_SELF)
 *
 * This class defines a covariant version of equals(). To correctly override the equals() method in java.lang.Object, the parameter of equals()
 * must have type java.lang.Object.
 * @author acraciun
 */
@Pattern
public class EqualsAbstractSelf {
	@Override
	public boolean equals(@VariableMatching(matchTypeOpposite = true) Object obj) {
		return someBooleanValue();
	}
}
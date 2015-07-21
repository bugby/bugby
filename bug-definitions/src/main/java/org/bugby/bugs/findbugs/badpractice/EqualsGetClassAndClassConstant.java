package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someBooleanValue;
import static org.bugby.wildcard.Wildcards.someExpressionUsing;

import org.bugby.api.annotation.ModifiersMatching;
import org.bugby.api.annotation.Pattern;

/**
 * Eq: equals method fails for subtypes (EQ_GETCLASS_AND_CLASS_CONSTANT)
 *
 * This class has an equals method that will be broken if it is inherited by subclasses. It compares a class literal with the class of the
 * argument (e.g., in class Foo it might check if Foo.class == o.getClass()). It is better to check if this.getClass() == o.getClass().
 *
 * @author acraciun
 */
@Pattern
@ModifiersMatching(FINAL = true)
public class EqualsGetClassAndClassConstant {
	@Override
	public boolean equals(Object obj) {
		someExpressionUsing(EqualsGetClassAndClassConstant.class == obj.getClass());
		return someBooleanValue();
	}
}

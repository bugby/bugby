package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someBooleanValue;

import org.bugby.api.annotation.Pattern;

/**
 * Eq: Equals checks for incompatible operand (EQ_CHECK_FOR_OPERAND_NOT_COMPATIBLE_WITH_THIS)
 * 
 * This equals method is checking to see if the argument is some incompatible type (i.e., a class that is neither a supertype nor subtype of the
 * class that defines the equals method). For example, the Foo class might have an equals method that looks like:
 * 
 * public boolean equals(Object o) { if (o instanceof Foo) return name.equals(((Foo)o).name); else if (o instanceof String) return
 * name.equals(o); else return false; This is considered bad practice, as it makes it very hard to implement an equals method that is symmetric
 * and transitive. Without those properties, very unexpected behavoirs are possible.
 * 
 * @author acraciun
 */
@Pattern
public class EqualsCheckForOperandNotCompatibleWithThis {
	@Override
	public boolean equals(Object o) {
		//TODO
		//someExpressionUsing(o instanceof SomeTypeIncompatibleWith<EqualsCheckForOperandNotCompatibleWithThis>);
		return someBooleanValue();
	}
}

package org.bugby.pattern.example.findbugs.correctness;

import static org.bugby.wildcard.Wildcards.someStringLiteral;
import static org.bugby.wildcard.Wildcards.someValue;

import com.google.common.base.Preconditions;

/**
 *
 * DMI: Reversed method arguments (DMI_ARGUMENTS_WRONG_ORDER) The arguments to this method call seem to be in the wrong order. For example, a
 * call Preconditions.checkNotNull("message", message) has reserved arguments: the value to be checked is the first argument.
 * 
 * @author acraciun
 */
public class ArgumentsWrongOrder {
	public void someCode() {
		Preconditions.checkNotNull(someStringLiteral(), someValue());
		//see impl - check other cases
	}
}

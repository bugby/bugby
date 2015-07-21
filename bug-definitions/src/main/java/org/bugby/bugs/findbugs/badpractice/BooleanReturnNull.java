package org.bugby.bugs.findbugs.badpractice;

import org.bugby.api.annotation.Pattern;

/**
 *
 * NP: Method with Boolean return type returns explicit null (NP_BOOLEAN_RETURN_NULL) A method that returns either Boolean.TRUE, Boolean.FALSE or
 * null is an accident waiting to happen. This method can be invoked as though it returned a value of type boolean, and the compiler will insert
 * automatic unboxing of the Boolean value. If a null value is returned, this will result in a NullPointerException.
 *
 * @author acraciun
 */
@Pattern
public class BooleanReturnNull {
	public Boolean method() {
		//TODO null or possible null here
		return null;
	}
}

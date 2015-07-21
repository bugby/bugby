package org.bugby.bugs.findbugs.badpractice;

import org.bugby.api.annotation.Pattern;

/**
 * NP: toString method may return null (NP_TOSTRING_COULD_RETURN_NULL) This toString method seems to return null in some circumstances. A liberal
 * reading of the spec could be interpreted as allowing this, but it is probably a bad idea and could cause other code to break. Return the empty
 * string or some other appropriate string rather than null.
 *
 * @author acraciun
 */
@Pattern
public class ToStringCouldReturnNull {
	@Override
	public String toString() {
		//TODO null or possible null here
		return null;
	}
}
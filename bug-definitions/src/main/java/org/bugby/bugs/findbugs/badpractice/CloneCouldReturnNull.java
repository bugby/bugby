package org.bugby.bugs.findbugs.badpractice;

import org.bugby.api.annotation.Pattern;

/**
 *
 * NP: Clone method may return null (NP_CLONE_COULD_RETURN_NULL) This clone method seems to return null in some circumstances, but clone is never
 * allowed to return a null value. If you are convinced this path is unreachable, throw an AssertionError instead.
 *
 * @author acraciun
 */
@Pattern
public class CloneCouldReturnNull {
	@Override
	public Object clone() {
		//TODO null or possible null here
		return null;
	}
}

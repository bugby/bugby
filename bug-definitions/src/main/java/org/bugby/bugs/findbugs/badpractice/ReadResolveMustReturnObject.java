package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.api.annotation.Pattern;
import org.bugby.wildcard.type.SomeTypeExcepting;

/**
 *
 * Se: The readResolve method must be declared with a return type of Object. (SE_READ_RESOLVE_MUST_RETURN_OBJECT) In order for the readResolve
 * method to be recognized by the serialization mechanism, it must be declared to have a return type of Object.
 *
 * 
 * @author acraciun
 */
@Pattern
public class ReadResolveMustReturnObject {
	public SomeTypeExcepting<Object> readResolve() {
		return someValue();
	}
}

package org.bugby.bugs.findbugs.badpractice;

import org.bugby.api.annotation.ModifiersMatching;
import org.bugby.api.annotation.Pattern;

/**
 *
 * Se: Non-serializable value stored into instance field of a serializable class (SE_BAD_FIELD_STORE) A non-serializable value is stored into a
 * non-transient field of a serializable class.
 * 
 * @author acraciun
 */
@Pattern
public class SerializableBadFieldStore {
	@ModifiersMatching(TRANSIENT = true)
	//private Serializable field = new NonSAndEType();
	//OR - check impl
	public void someCode() {
		//TODO fix this
		//field = new NonSAndEType();
	}
}
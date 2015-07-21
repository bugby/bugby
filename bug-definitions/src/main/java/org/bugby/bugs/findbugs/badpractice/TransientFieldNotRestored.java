package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.WildcardAnnotations.$Missing;
import static org.bugby.wildcard.Wildcards.someValue;

import java.io.ObjectInputStream;

import org.bugby.api.annotation.ModifiersMatching;
import org.bugby.api.annotation.OrSet;
import org.bugby.api.annotation.Pattern;

/**
 *
 * Se: Transient field that isn't set by deserialization. (SE_TRANSIENT_FIELD_NOT_RESTORED) This class contains a field that is updated at
 * multiple places in the class, thus it seems to be part of the state of the class. However, since the field is marked as transient and not set
 * in readObject or readResolve, it will contain the default value in any deserialized instance of the class
 *
 * @author acraciun
 */
@Pattern
public class TransientFieldNotRestored {
	@ModifiersMatching(TRANSIENT = true)
	private transient Object field;

	public void someCode() {
		field = someValue();
	}

	@OrSet
	private void readObject(ObjectInputStream stream) {
		$Missing();
		field = someValue();
	}

	@OrSet
	private Object readResolve() {
		$Missing();
		field = someValue();
		return someValue();
	}
}

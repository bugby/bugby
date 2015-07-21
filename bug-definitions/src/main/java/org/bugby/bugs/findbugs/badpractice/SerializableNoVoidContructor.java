package org.bugby.bugs.findbugs.badpractice;

import java.io.Serializable;

import org.bugby.api.annotation.MethodMatching;
import org.bugby.api.annotation.Missing;
import org.bugby.api.annotation.Pattern;
import org.bugby.api.annotation.PatternListMatchingType;

/**
 *
 * Se: Class is Serializable but its superclass doesn't define a void constructor (SE_NO_SUITABLE_CONSTRUCTOR) This class implements the
 * Serializable interface and its superclass does not. When such an object is deserialized, the fields of the superclass need to be initialized
 * by invoking the void constructor of the superclass. Since the superclass does not have one, serialization and deserialization will fail at
 * runtime.
 * 
 * 
 * @author acraciun
 */
@Pattern
@SuppressWarnings("serial")
public class SerializableNoVoidContructor implements Serializable {
	@MethodMatching(matchParameters = PatternListMatchingType.exact)
	@Missing
	public SerializableNoVoidContructor() {
	}
}

package org.bugby.bugs.findbugs.badpractice;

import java.io.Serializable;

import org.bugby.api.annotation.MethodMatching;
import org.bugby.api.annotation.Missing;
import org.bugby.api.annotation.Pattern;
import org.bugby.api.annotation.PatternListMatchingType;

/**
 * Se: Class is Externalizable but doesn't define a void constructor (SE_NO_SUITABLE_CONSTRUCTOR_FOR_EXTERNALIZATION) This class implements the
 * Externalizable interface, but does not define a void constructor. When Externalizable objects are deserialized, they first need to be
 * constructed by invoking the void constructor. Since this class does not have one, serialization and deserialization will fail at runtime.
 *
 * 
 *
 * @author acraciun
 */
@SuppressWarnings("serial")
@Pattern
public class ExternalizableNoVoidContructor implements Serializable {
	@MethodMatching(matchParameters = PatternListMatchingType.exact)
	@Missing
	public ExternalizableNoVoidContructor() {
	}
}

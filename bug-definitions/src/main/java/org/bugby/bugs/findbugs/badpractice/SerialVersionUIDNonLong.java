package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someTypedValue;

import org.bugby.api.annotation.Pattern;
import org.bugby.api.annotation.VariableMatching;
import org.bugby.wildcard.type.SomeTypeExcepting;

/**
 *
 * Se: serialVersionUID isn't long (SE_NONLONG_SERIALVERSIONID) This class defines a serialVersionUID field that is not long. The field should be
 * made long if it is intended to specify the version UID for purposes of serialization.
 *
 * @author acraciun
 */
@Pattern
public class SerialVersionUIDNonLong {
	@SuppressWarnings("unchecked")
	@VariableMatching(matchName = true)
	private SomeTypeExcepting<Long> serialVersionUID = someTypedValue(SomeTypeExcepting.class);
}

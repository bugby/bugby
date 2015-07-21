package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someLongValue;

import org.bugby.api.annotation.ModifiersMatching;
import org.bugby.api.annotation.Pattern;
import org.bugby.api.annotation.VariableMatching;

/**
 *
 * Se: serialVersionUID isn't static (SE_NONSTATIC_SERIALVERSIONID) This class defines a serialVersionUID field that is not static. The field
 * should be made static if it is intended to specify the version UID for purposes of serialization
 *
 * @author acraciun
 */
@Pattern
public class SerialVersionUIDNonStatic {
	@VariableMatching(matchName = true)
	@ModifiersMatching(STATIC = true)
	private long serialVersionUID = someLongValue();
}

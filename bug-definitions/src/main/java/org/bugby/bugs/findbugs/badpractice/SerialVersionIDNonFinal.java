package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someLongValue;

import org.bugby.api.annotation.ModifiersMatching;
import org.bugby.api.annotation.Pattern;
import org.bugby.api.annotation.VariableMatching;

/**
 *
 * Se: serialVersionUID isn't final (SE_NONFINAL_SERIALVERSIONID) This class defines a serialVersionUID field that is not final. The field should
 * be made final if it is intended to specify the version UID for purposes of serialization.
 * 
 * 
 * @author acraciun
 */
@Pattern
public class SerialVersionIDNonFinal {
	@VariableMatching(matchName = true)
	@ModifiersMatching(FINAL = true)
	private long serialVersionUID = someLongValue();
}

package org.bugby.bugs.findbugs.badpractice;

import org.bugby.api.annotation.ModifiersMatching;
import org.bugby.api.annotation.Pattern;
import org.bugby.wildcard.type.SomeType;

/**
 *
 * ME: Enum field is public and mutable (ME_MUTABLE_ENUM_FIELD) A mutable public field is defined inside a public enum, thus can be changed by
 * malicious code or by accident from another package. Though mutable enum fields may be used for lazy initialization, it's a bad practice to
 * expose them to the outer world. Consider declaring this field final and/or package-private.
 *
 * @author acraciun
 */
@Pattern
public enum MutableEnumField {
	;
	@ModifiersMatching(VISIBILITY = true, FINAL = true)
	public SomeType field;
}

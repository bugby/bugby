package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.begin;
import static org.bugby.wildcard.Wildcards.end;
import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.api.annotation.Pattern;

/**
 *
 * ME: Public enum method unconditionally sets its field (ME_ENUM_FIELD_SETTER) This public method declared in public enum unconditionally sets
 * enum field, thus this field can be changed by malicious code or by accident from another package. Though mutable enum fields may be used for
 * lazy initialization, it's a bad practice to expose them to the outer world. Consider removing this method or declaring it package-private.
 *
 * @author acraciun
 */
@Pattern
public enum EnumFieldSetter {
	;
	private Object field;

	public void setter() {
		begin();
		field = someValue();
		end();
	}
}

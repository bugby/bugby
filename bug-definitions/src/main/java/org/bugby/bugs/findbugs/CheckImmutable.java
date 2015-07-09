package org.bugby.bugs.findbugs;

import javax.annotation.concurrent.Immutable;

import org.bugby.api.IgnoreFromMatching;
import org.bugby.api.Pattern;
import org.bugby.wildcard.SomeType;

@Pattern
@Immutable
public class CheckImmutable {
	private final Object someField;

	/**
	 * i need this constructor only for eclipse to allow the final field. this is not part of the matching
	 */
	@IgnoreFromMatching
	public CheckImmutable(SomeType someField) {
		this.someField = someField;
	}
}

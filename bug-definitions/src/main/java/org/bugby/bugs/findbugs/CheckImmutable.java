package org.bugby.bugs.findbugs;

import javax.annotation.concurrent.Immutable;

import org.bugby.annotation.GoodExample;
import org.bugby.annotation.IgnoreFromMatching;
import org.bugby.wildcard.SomeType;

@GoodExample
@Immutable
public class CheckImmutable {
	private final SomeType someField;

	/**
	 * i need this constructor only for eclipse to allow the final field. this is not part of the matching
	 */
	@IgnoreFromMatching
	public CheckImmutable(SomeType someField) {
		this.someField = someField;
	}
}

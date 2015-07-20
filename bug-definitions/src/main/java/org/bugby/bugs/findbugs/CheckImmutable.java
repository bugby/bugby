package org.bugby.bugs.findbugs;

import javax.annotation.concurrent.Immutable;

import org.bugby.api.annotation.ModifiersMatching;
import org.bugby.api.annotation.Pattern;

@Pattern
@Immutable
public class CheckImmutable {
	@ModifiersMatching(FINAL = true)
	private Object someField;

}

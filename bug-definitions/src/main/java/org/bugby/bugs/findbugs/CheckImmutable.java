package org.bugby.bugs.findbugs;

import javax.annotation.concurrent.Immutable;

import org.bugby.api.Pattern;
import org.bugby.matcher.declaration.ModifiersMatching;

@Pattern
@Immutable
public class CheckImmutable {
	@ModifiersMatching(FINAL = true)
	private Object someField;

}

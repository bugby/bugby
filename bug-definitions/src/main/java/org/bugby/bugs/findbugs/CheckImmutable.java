package org.bugby.bugs.findbugs;

import javax.annotation.concurrent.Immutable;

import org.bugby.matcher.declaration.ModifiersMatching;
import org.bugby.wildcard.Pattern;

@Pattern
@Immutable
public class CheckImmutable {
	@ModifiersMatching(FINAL = true)
	private Object someField;

}

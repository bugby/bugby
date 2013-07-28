package org.bugby.bugs.findbugs;

import javax.annotation.concurrent.Immutable;

import org.bugby.annotation.GoodExampleTrigger;
import org.bugby.wildcard.SomeType;

@GoodExampleTrigger(
		forExample = CheckImmutable.class)
@Immutable
public class CheckImmutableTrigger {
	private SomeType someField;
}

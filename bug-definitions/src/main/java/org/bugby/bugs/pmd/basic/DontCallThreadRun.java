package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.someTypedValue;

import org.bugby.api.annotation.Pattern;

@Pattern
public class DontCallThreadRun {
	public void someCode() {
		someTypedValue(Thread.class).run();
	}
}

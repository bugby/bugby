package org.bugby.bugs.pmd;

import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.annotation.BadExample;
import org.bugby.annotation.IgnoreFromMatching;

@BadExample
public class DontCallThreadRun {
	public void someCode() {
		@IgnoreFromMatching
		Thread t = someValue();

		t.run();
	}
}

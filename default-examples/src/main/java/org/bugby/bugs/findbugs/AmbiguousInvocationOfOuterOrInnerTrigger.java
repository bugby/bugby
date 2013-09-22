package org.bugby.bugs.findbugs;

import org.bugby.annotation.GoodExampleTrigger;

@GoodExampleTrigger(forExample = AmbiguousInvocationOfOuterOrInner.class)
public class AmbiguousInvocationOfOuterOrInnerTrigger {

	public void someMethod() {

	}

	public class SomeType2 extends SomeType1 {
		public void someCode() {
			someMethod();
		}
	}
}

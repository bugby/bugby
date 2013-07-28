package org.bugby.bugs.findbugs;

import org.bugby.annotation.GoodExampleTrigger;

@GoodExampleTrigger(
		forExample = AmbiguousInvocationOfOuterOrInner.class)
public class AmbiguousInvocationOfOuterOrInnerTrigger {

	public void method() {

	}

	public class InnerType extends BaseType {
		public void someCode() {
			method();
		}
	}
}

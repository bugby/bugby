package org.bugby.bugs.findbugs;

import org.bugby.annotation.GoodExample;

@GoodExample
public class AmbiguousInvocationOfOuterOrInner {

	public void someMethod() {

	}

	public class SomeType2 extends SomeType1 {
		public void someCode() {
			super.someMethod();
		}
	}
}

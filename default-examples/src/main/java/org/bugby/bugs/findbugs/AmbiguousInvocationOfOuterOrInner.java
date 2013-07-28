package org.bugby.bugs.findbugs;

import org.bugby.annotation.GoodExample;

@GoodExample
public class AmbiguousInvocationOfOuterOrInner {

	public void method() {

	}

	public class InnerType extends BaseType {
		public void someCode() {
			super.method();
		}
	}
}

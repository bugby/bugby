package org.bugby.bugs.findbugs;

import org.bugby.annotation.BadExample;
import org.bugby.api.wildcard.Correlation;
import org.bugby.wildcard.correlation.SameMethodSignature;

@BadExample
public class AmbiguousInvocationOfOuterOrInner {

	@Correlation(key = "sameOuterAndInnerSuper", comparator = SameMethodSignature.class)
	public void someMethod() {
	}

	public class SomeType2 extends BaseClass {
		public void someCode() {
			someMethod();
		}
	}
}

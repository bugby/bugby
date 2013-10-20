package org.bugby.bugs.findbugs;

import org.bugby.annotation.BadExample;
import org.bugby.wildcard.api.Correlation;
import org.bugby.wildcard.correlation.SameMethodSignature;

@BadExample
public class AmbiguousInvocationOfOuterOrInner {

	@Correlation(key = "sameOuterAndInnerSuper", comparator = SameMethodSignature.class)
	public void someMethod() {

	}

	public class SomeType1 {

		@Correlation(key = "sameOuterAndInnerSuper", comparator = SameMethodSignature.class)
		public void someMethod() {

		}
	}

	public class SomeType2 extends SomeType1 {
		public void someCode() {
			someMethod();
		}
	}
}

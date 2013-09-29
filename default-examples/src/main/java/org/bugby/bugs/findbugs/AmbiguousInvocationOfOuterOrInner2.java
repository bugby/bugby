package org.bugby.bugs.findbugs;

import org.bugby.annotation.GoodExample;
import org.bugby.wildcard.api.Correlation;
import org.bugby.wildcard.correlation.SameMethodSignature;

@GoodExample
public class AmbiguousInvocationOfOuterOrInner2 {

	@Correlation(key = "sameOuterAndInner", comparator = SameMethodSignature.class)
	public void someMethod2() {

	}

	public class SomeType2 {

		@Correlation(key = "sameOuterAndInner", comparator = SameMethodSignature.class)
		public void someMethod2() {

		}
	}
}

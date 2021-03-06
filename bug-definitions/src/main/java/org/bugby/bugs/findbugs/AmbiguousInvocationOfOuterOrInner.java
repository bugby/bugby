package org.bugby.bugs.findbugs;

import org.bugby.api.annotation.Correlation;
import org.bugby.api.annotation.Pattern;
import org.bugby.wildcard.correlation.SameMethodSignature;

@Pattern
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

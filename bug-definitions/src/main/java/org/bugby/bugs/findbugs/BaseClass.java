package org.bugby.bugs.findbugs;

import org.bugby.api.annotation.Correlation;
import org.bugby.wildcard.correlation.SameMethodSignature;

public class BaseClass {

	@Correlation(key = "sameOuterAndInnerSuper", comparator = SameMethodSignature.class)
	public void someMethod() {

	}
}
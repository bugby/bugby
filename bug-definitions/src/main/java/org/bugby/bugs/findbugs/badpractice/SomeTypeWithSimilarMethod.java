package org.bugby.bugs.findbugs.badpractice;

import org.bugby.api.annotation.Correlation;
import org.bugby.api.annotation.Pattern;
import org.bugby.wildcard.correlation.SimilarName;

@Pattern(root = true)
public class SomeTypeWithSimilarMethod {
	@Correlation(key = "similarName", comparator = SimilarName.class)
	public void method1() {
	}
}

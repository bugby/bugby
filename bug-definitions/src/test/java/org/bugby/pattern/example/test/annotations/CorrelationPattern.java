package org.bugby.pattern.example.test.annotations;

import org.bugby.api.annotation.Correlation;
import org.bugby.api.annotation.Pattern;
import org.bugby.wildcard.correlation.SameVariableType;

@Pattern
public class CorrelationPattern {
	@Correlation(key = "checkType", comparator = SameVariableType.class)
	public Object field;

	public void method(@Correlation(key = "checkType", comparator = SameVariableType.class) Object param) {

	}
}

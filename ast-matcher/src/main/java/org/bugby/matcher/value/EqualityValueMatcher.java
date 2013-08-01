package org.bugby.matcher.value;

public class EqualityValueMatcher<T> implements ValueMatcher<T> {

	@Override
	public boolean matches(T actualValue, T patternValue) {
		return actualValue.equals(patternValue);
	}

}

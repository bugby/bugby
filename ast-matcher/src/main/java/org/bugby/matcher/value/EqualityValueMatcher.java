package org.bugby.matcher.value;

import org.bugby.matcher.tree.Pattern;

public class EqualityValueMatcher<T> implements ValueMatcher<T> {

	@Override
	public boolean matches(Pattern<T> pattern, T value) {
		return pattern.getExampleValue().equals(value);
	}

}

package org.bugby.pattern.api.matcher.content;

import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringMatcher implements ContentMatcher<String> {

	private final Pattern pattern;

	@Override
	public boolean matches(String input) {
		return pattern.matcher(input).matches();
	}
}

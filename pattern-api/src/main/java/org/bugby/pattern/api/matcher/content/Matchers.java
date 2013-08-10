package org.bugby.pattern.api.matcher.content;

import java.util.regex.Pattern;

import javax.lang.model.type.TypeMirror;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Matchers {

	public static final <T> ContentMatcher<T> any() {
		return new AnyMatcher<T>();
	}

	public static final ContentMatcher<TypeMirror> specificClass(Class<?> clazz) {
		return new SpecificClassTypeMirrorMatcher(clazz);
	}

	public static final <T> ContentMatcher<String> string(String pattern) {
		return new StringMatcher(Pattern.compile(pattern));
	}

}

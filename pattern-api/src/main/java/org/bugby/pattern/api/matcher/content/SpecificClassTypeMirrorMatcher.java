package org.bugby.pattern.api.matcher.content;

import javax.lang.model.type.TypeMirror;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SpecificClassTypeMirrorMatcher implements ContentMatcher<TypeMirror> {

	private final Class<?> clazz;

	@Override
	public boolean matches(TypeMirror input) {
		//TODO lots of fun here
		throw new UnsupportedOperationException("notimplemented yet");
	}

}

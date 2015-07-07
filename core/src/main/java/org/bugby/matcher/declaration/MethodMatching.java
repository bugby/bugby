package org.bugby.matcher.declaration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.bugby.api.PatternConfig;
import org.bugby.api.PatternListMatchingType;

@Retention(RetentionPolicy.RUNTIME)
@PatternConfig
public @interface MethodMatching {
	public boolean matchName() default false;

	public boolean matchReturnType() default true;

	public PatternListMatchingType matchTypeParameters() default PatternListMatchingType.partial;

	public PatternListMatchingType matchParameters() default PatternListMatchingType.partial;

	public PatternListMatchingType matchThrows() default PatternListMatchingType.partial;
}
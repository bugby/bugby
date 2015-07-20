package org.bugby.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@PatternConfig
public @interface MethodMatching {
	public boolean matchName() default false;

	public boolean matchReturnType() default true;

	public PatternListMatchingType matchTypeParameters() default PatternListMatchingType.partial;

	public PatternListMatchingType matchParameters() default PatternListMatchingType.partial;

	public PatternListMatchingType matchThrows() default PatternListMatchingType.partial;
}

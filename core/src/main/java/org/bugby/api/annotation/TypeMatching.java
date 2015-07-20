package org.bugby.api.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PatternConfig
public @interface TypeMatching {
	public boolean matchName() default false;

	public PatternListMatchingType matchExtends() default PatternListMatchingType.partial;

	public PatternListMatchingType matchImplements() default PatternListMatchingType.partial;

	public PatternListMatchingType matchTypeParameters() default PatternListMatchingType.partial;

	public PatternListMatchingType matchMethods() default PatternListMatchingType.partial;

	public PatternListMatchingType matchFields() default PatternListMatchingType.partial;
}

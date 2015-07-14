package org.bugby.matcher.statement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.bugby.api.PatternConfig;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER})
@PatternConfig
public @interface VariableMatching {
	public boolean matchName() default false;

	public boolean matchType() default true;

	public boolean matchInitializer() default true;

}

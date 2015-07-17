package org.bugby.matcher.statement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.bugby.wildcard.PatternConfig;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.PARAMETER})
@PatternConfig
public @interface VariableMatching {
	public boolean matchName() default false;

	public boolean matchType() default true;

	public boolean matchInitializer() default true;

	//TODO need to think this through - the idea is to be able to tell that it's ok to have anytype EXCEPTING the one listed
	public boolean matchTypeOpposite() default false;

}

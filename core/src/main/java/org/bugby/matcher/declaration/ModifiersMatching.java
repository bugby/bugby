package org.bugby.matcher.declaration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.bugby.wildcard.PatternConfig;

@Retention(RetentionPolicy.RUNTIME)
@PatternConfig
public @interface ModifiersMatching {
	public boolean VISIBILITY() default false;

	public boolean ABSTRACT() default false;

	public boolean STATIC() default false;

	public boolean FINAL() default false;

	public boolean TRANSIENT() default false;

	public boolean VOLATILE() default false;

	public boolean SYNCHRONIZED() default false;

	public boolean NATIVE() default false;

	public boolean STRICTFP() default false;

}

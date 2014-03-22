package org.bugby.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface Matching {
	MatchingType inheritance() default MatchingType.ANY;
}

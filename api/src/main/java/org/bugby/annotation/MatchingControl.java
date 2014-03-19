package org.bugby.annotation;

public @interface MatchingControl {
	boolean hasNothingBefore() default false;

	boolean hasNothingAfter() default false;
}

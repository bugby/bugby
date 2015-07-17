package org.bugby.wildcard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.bugby.api.TreeMatcher;

//@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@PatternConfig
public @interface Pattern {
	/**
	 * this is the matcher that will be build with the annotated Tree node. If the value is missing, than the default
	 * matcher for the given pattern node type is taken.
	 */
	Class<? extends TreeMatcher> value() default TreeMatcher.class;

	/**
	 * it needs to be false for the types that are used together with other root pattern. They are never used to detect
	 * bugs directly.
	 */
	boolean root() default true;
}

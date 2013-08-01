package org.bugby.wildcard.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Wildcard {
	Class<? extends WildcardNodeMatcher<?>> matcher();
}

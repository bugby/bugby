package org.bugby.api.wildcard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Wildcard {
	Class<? extends WildcardNodeMatcher> value();
}

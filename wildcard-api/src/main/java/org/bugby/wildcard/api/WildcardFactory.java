package org.bugby.wildcard.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface WildcardFactory {
	Class<? extends WildcardNodeMatcherFactory> value();
}

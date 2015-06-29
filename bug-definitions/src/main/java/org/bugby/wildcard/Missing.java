package org.bugby.wildcard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.bugby.api.wildcard.Wildcard;
import org.bugby.wildcard.matcher.code.MissingMatcher;

@Retention(RetentionPolicy.RUNTIME)
@Wildcard(MissingMatcher.class)
public @interface Missing {

}

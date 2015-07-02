package org.bugby.wildcard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.bugby.api.Pattern;
import org.bugby.matcher.wildcard.code.MissingMatcher;

@Retention(RetentionPolicy.RUNTIME)
@Pattern(MissingMatcher.class)
public @interface Missing {

}
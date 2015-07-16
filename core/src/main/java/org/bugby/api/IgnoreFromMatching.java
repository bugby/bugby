package org.bugby.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PatternConfig
public @interface IgnoreFromMatching {

}

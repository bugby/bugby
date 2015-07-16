package org.bugby.wildcard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.bugby.api.Pattern;
import org.bugby.api.PatternConfig;
import org.bugby.matcher.wildcard.code.MatchCountMatcher;

@Retention(RetentionPolicy.RUNTIME)
@Pattern(MatchCountMatcher.class)
@PatternConfig
public @interface MatchCount {
	public int min() default 0;

	public int max() default Integer.MAX_VALUE;
}

package org.bugby.wildcard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.bugby.api.wildcard.Wildcard;
import org.bugby.wildcard.matcher.MatchCountMatcher;

@Retention(RetentionPolicy.RUNTIME)
@Wildcard(MatchCountMatcher.class)
public @interface MatchCount {
	public int min() default 0;

	public int max() default Integer.MAX_VALUE;
}

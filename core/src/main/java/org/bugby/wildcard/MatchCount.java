package org.bugby.wildcard;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.bugby.api.Pattern;
import org.bugby.matcher.wildcard.code.MatchCountMatcher;

@Retention(RetentionPolicy.RUNTIME)
@Pattern(MatchCountMatcher.class)
public @interface MatchCount {
	public int min() default 0;

	public int max() default Integer.MAX_VALUE;
}

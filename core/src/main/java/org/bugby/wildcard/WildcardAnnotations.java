package org.bugby.wildcard;

import org.bugby.api.Pattern;
import org.bugby.matcher.wildcard.code.LastMatcher;

public class WildcardAnnotations {
	@Pattern(LastMatcher.class)
	public static void $Last() {

	}

	public static void $AnyLoop() {

	}
}

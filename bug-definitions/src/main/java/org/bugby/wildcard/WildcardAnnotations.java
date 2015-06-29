package org.bugby.wildcard;

import org.bugby.api.wildcard.Wildcard;
import org.bugby.wildcard.matcher.code.LastMatcher;

public class WildcardAnnotations {
	@Wildcard(LastMatcher.class)
	public static void $Last() {

	}

	public static void $AnyLoop() {

	}
}

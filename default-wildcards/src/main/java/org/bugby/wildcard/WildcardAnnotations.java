package org.bugby.wildcard;

import org.bugby.wildcard.api.WildcardFactory;
import org.bugby.wildcard.matcher.LastMatcherFactory;

public class WildcardAnnotations {
	@WildcardFactory(LastMatcherFactory.class)
	public static void $Last() {

	}

	public static void $AnyLoop() {

	}
}

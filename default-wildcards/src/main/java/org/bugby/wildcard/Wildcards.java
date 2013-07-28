package org.bugby.wildcard;

import org.bugby.wildcard.api.Wildcard;
import org.bugby.wildcard.api.WildcardDictionary;
import org.bugby.wildcard.matcher.SomeFieldMatcher;
import org.bugby.wildcard.matcher.SomeParamMatcher;
import org.bugby.wildcard.matcher.SomeValueMatcher;

@WildcardDictionary
public class Wildcards {
	/**
	 * matches any field
	 */
	@Wildcard(
			matcher = SomeFieldMatcher.class)
	public static SomeType someField;

	/**
	 * matches any parameter
	 */
	@Wildcard(
			matcher = SomeParamMatcher.class)
	public static SomeType someParameter;

	/**
	 * matches any expression of any type
	 * 
	 * @return
	 */
	@Wildcard(
			matcher = SomeValueMatcher.class)
	public static <T> T someValue() {
		return null;
	}

	@Wildcard(
			matcher = SomeValueMatcher.class)
	public static <T> T someValue(Class<T> type) {
		return null;
	}

	public static void someCode() {

	}
}

package org.bugby.wildcard;

import org.bugby.wildcard.api.Wildcard;
import org.bugby.wildcard.api.WildcardDictionary;
import org.bugby.wildcard.matcher.SomeCodeMatcher;
import org.bugby.wildcard.matcher.SomeConditionMatcher;
import org.bugby.wildcard.matcher.SomeFieldMatcher;
import org.bugby.wildcard.matcher.SomeParamMatcher;
import org.bugby.wildcard.matcher.SomeValueMatcher;

@WildcardDictionary
public class Wildcards {
	// LITERALS
	public static int someInteger = 0;
	public static long someLong = 0;
	public static boolean someBoolean = true;

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
	public static <T> T someTypedValue(Class<T> type) {
		return null;
	}

	@Wildcard(
			matcher = SomeCodeMatcher.class)
	public static void someCode() {
	}

	@Wildcard(
			matcher = SomeConditionMatcher.class)
	public static boolean someCondition() {
		return true;
	}

	public static <T> T someExpressionUsing(Object value) {
		return null;
	}

	@Wildcard(
			matcher = SomeConditionMatcher.class)
	public static boolean someConditionUsing(Object value) {
		return true;
	}

}

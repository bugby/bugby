package org.bugby.wildcard;

import org.bugby.wildcard.api.Wildcard;
import org.bugby.wildcard.matcher.BeginMatcher;
import org.bugby.wildcard.matcher.EndMatcher;
import org.bugby.wildcard.matcher.NoCodeMatcher;
import org.bugby.wildcard.matcher.SomeCodeMatcher;
import org.bugby.wildcard.matcher.SomeConditionMatcher;
import org.bugby.wildcard.matcher.SomeConditionUsingMatcher;
import org.bugby.wildcard.matcher.SomeExpressionUsingMatcher;
import org.bugby.wildcard.matcher.SomeFieldMatcher;
import org.bugby.wildcard.matcher.SomeMethodMatcher;
import org.bugby.wildcard.matcher.SomeParamMatcher;
import org.bugby.wildcard.matcher.SomeValueMatcher;
import org.bugby.wildcard.matcher.SomeVariableMatcher;

@SuppressWarnings("unused")
public class Wildcards {
	// LITERALS
	public static int someInteger = 0;
	public static long someLong = 0;
	public static boolean someBoolean = true;

	/**
	 * matches any field
	 */
	@Wildcard(matcher = SomeFieldMatcher.class)
	public static SomeType someField;

	/**
	 * matches any parameter
	 */
	@Wildcard(matcher = SomeParamMatcher.class)
	public static SomeType someParameter;

	/**
	 * matches any variable
	 */
	@Wildcard(matcher = SomeVariableMatcher.class)
	public static SomeType someVar;

	/**
	 * matches any expression of any type
	 * 
	 * @return
	 */
	@Wildcard(matcher = SomeValueMatcher.class)
	public static <T> T someValue() {
		return null;
	}

	@Wildcard(matcher = SomeValueMatcher.class)
	public static <T> T someTypedValue(Class<T> type) {
		return null;
	}

	@Wildcard(matcher = SomeCodeMatcher.class)
	public static void someCode() {//
	}

	@Wildcard(matcher = SomeConditionMatcher.class)
	public static boolean someCondition() {
		return true;
	}

	@Wildcard(matcher = SomeExpressionUsingMatcher.class)
	public static <T> T someExpressionUsing(Object value) {
		return null;
	}

	@Wildcard(matcher = SomeConditionUsingMatcher.class)
	public static boolean someConditionUsing(Object value) {
		return true;
	}

	@Wildcard(matcher = BeginMatcher.class)
	public static void begin() {
		//
	}

	@Wildcard(matcher = EndMatcher.class)
	public static void end() {
		//
	}

	@Wildcard(matcher = NoCodeMatcher.class)
	public static void noCode() {
		//
	}

	@Wildcard(matcher = SomeMethodMatcher.class)
	public static void someMethod() {
		//
	}
}

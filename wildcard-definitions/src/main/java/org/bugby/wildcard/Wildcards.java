package org.bugby.wildcard;

import org.bugby.api.wildcard.Wildcard;
import org.bugby.api.wildcard.WildcardAnnotation;
import org.bugby.wildcard.matcher.AnyBranchMatcher;
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
	// annotations
	@WildcardAnnotation
	public @interface IgnoreInitialization {

	}

	// LITERALS
	public static int someInteger = 0;
	public static long someLong = 0;
	public static boolean someBoolean = true;

	/**
	 * matches any field
	 */
	@Wildcard(SomeFieldMatcher.class)
	public static SomeType someField;

	/**
	 * matches any parameter
	 */
	@Wildcard(SomeParamMatcher.class)
	public static SomeType someParam;

	/**
	 * matches any variable
	 */
	@Wildcard(SomeVariableMatcher.class)
	public static SomeType someVar;

	/**
	 * matches any expression of any type
	 * 
	 * @return
	 */
	@Wildcard(SomeValueMatcher.class)
	public static <T> T someValue() {
		return null;
	}

	@Wildcard(SomeValueMatcher.class)
	public static <T> T someTypedValue(Class<T> type) {
		return null;
	}

	@Wildcard(SomeCodeMatcher.class)
	public static void someCode() {//
	}

	@Wildcard(SomeConditionMatcher.class)
	public static boolean someCondition() {
		return true;
	}

	@Wildcard(SomeExpressionUsingMatcher.class)
	public static <T> T someExpressionUsing(Object... value) {
		return null;
	}

	@Wildcard(SomeConditionUsingMatcher.class)
	public static boolean someConditionUsing(Object... value) {
		return true;
	}

	@Wildcard(BeginMatcher.class)
	public static void begin() {
		//
	}

	@Wildcard(EndMatcher.class)
	public static void end() {
		//
	}

	@Wildcard(NoCodeMatcher.class)
	public static void noCode() {
		//
	}

	@Wildcard(SomeMethodMatcher.class)
	public static void someMethod() {
		//
	}

	@Wildcard(AnyBranchMatcher.class)
	public static void anyBranch() {

	}
}

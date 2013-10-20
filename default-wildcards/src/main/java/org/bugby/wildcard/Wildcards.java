package org.bugby.wildcard;

import org.bugby.wildcard.api.Wildcard;
import org.bugby.wildcard.api.WildcardAnnotation;
import org.bugby.wildcard.api.WildcardFactory;
import org.bugby.wildcard.matcher.AnyBranchMatcher;
import org.bugby.wildcard.matcher.BeginMatcher;
import org.bugby.wildcard.matcher.EndMatcher;
import org.bugby.wildcard.matcher.NoCodeMatcher;
import org.bugby.wildcard.matcher.SomeCodeMatcherFactory;
import org.bugby.wildcard.matcher.SomeConditionMatcher;
import org.bugby.wildcard.matcher.SomeConditionUsingMatcherFactory;
import org.bugby.wildcard.matcher.SomeExpressionUsingMatcherFactory;
import org.bugby.wildcard.matcher.SomeFieldMatcher;
import org.bugby.wildcard.matcher.SomeMethodMatcherFactory;
import org.bugby.wildcard.matcher.SomeParamMatcher;
import org.bugby.wildcard.matcher.SomeValueMatcherFactory;
import org.bugby.wildcard.matcher.SomeVariableMatcherFactory;

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
	public static SomeType someParameter;

	/**
	 * matches any variable
	 */
	@WildcardFactory(SomeVariableMatcherFactory.class)
	public static SomeType someVar;

	/**
	 * matches any expression of any type
	 * 
	 * @return
	 */
	@WildcardFactory(SomeValueMatcherFactory.class)
	public static <T> T someValue() {
		return null;
	}

	@WildcardFactory(SomeValueMatcherFactory.class)
	public static <T> T someTypedValue(Class<T> type) {
		return null;
	}

	@WildcardFactory(SomeCodeMatcherFactory.class)
	public static void someCode() {//
	}

	@Wildcard(SomeConditionMatcher.class)
	public static boolean someCondition() {
		return true;
	}

	@WildcardFactory(SomeExpressionUsingMatcherFactory.class)
	public static <T> T someExpressionUsing(Object... value) {
		return null;
	}

	@WildcardFactory(SomeConditionUsingMatcherFactory.class)
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

	@WildcardFactory(SomeMethodMatcherFactory.class)
	public static void someMethod() {
		//
	}

	@Wildcard(AnyBranchMatcher.class)
	public static void anyBranch() {

	}
}

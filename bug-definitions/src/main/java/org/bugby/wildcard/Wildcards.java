package org.bugby.wildcard;

import org.bugby.api.wildcard.Wildcard;
import org.bugby.api.wildcard.WildcardAnnotation;
import org.bugby.wildcard.matcher.AnyBranchMatcher;
import org.bugby.wildcard.matcher.code.BeginMatcher;
import org.bugby.wildcard.matcher.code.EndMatcher;
import org.bugby.wildcard.matcher.code.NoCodeMatcher;
import org.bugby.wildcard.matcher.code.SomeCodeMatcher;
import org.bugby.wildcard.matcher.expr.SomeConditionMatcher;
import org.bugby.wildcard.matcher.expr.SomeConditionUsingMatcher;
import org.bugby.wildcard.matcher.expr.SomeExpressionThrowingMatcher;
import org.bugby.wildcard.matcher.expr.SomeExpressionUsingMatcher;
import org.bugby.wildcard.matcher.expr.SomeValueMatcher;
import org.bugby.wildcard.matcher.method.SomeMethodMatcher;
import org.bugby.wildcard.matcher.var.SomeFieldMatcher;
import org.bugby.wildcard.matcher.var.SomeParamMatcher;
import org.bugby.wildcard.matcher.var.SomeVariableMatcher;

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

	/**
	 * matches any expression. The clazz parameter that represents the class of the exception to be thrown is not checked. It has only the role
	 * to allow throwing the desired exception. This is usually used when the pattern concentrates more on the "catch" part of an block.
	 * @param clazz
	 * @return
	 * @throws T
	 */
	@Wildcard(SomeExpressionThrowingMatcher.class)
	public static <T extends Exception, V> V someExpressionThrowing(Class<T> clazz) throws T {//
		return null;
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

	@Wildcard(SomeConditionUsingMatcher.class)
	public static <T> T anywhere(Object... value) {
		return null;
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

	/**
	 * matches any block of code or an entire method. It can be used as a method definition only, not as a method call! Example:
	 *
	 * <pre>
	 * public void someCode() {
	 * 	//... other matchers
	 * }
	 * </pre>
	 */
	@Wildcard(SomeCodeMatcher.class)
	public static void someCode() {//
	}
}

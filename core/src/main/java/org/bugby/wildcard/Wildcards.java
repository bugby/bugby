package org.bugby.wildcard;

import org.bugby.api.Pattern;
import org.bugby.api.WildcardAnnotation;
import org.bugby.matcher.wildcard.AnyBranchMatcher;
import org.bugby.matcher.wildcard.code.BeginMatcher;
import org.bugby.matcher.wildcard.code.EndMatcher;
import org.bugby.matcher.wildcard.code.NoCodeMatcher;
import org.bugby.matcher.wildcard.code.SomeCodeMatcher;
import org.bugby.matcher.wildcard.expr.SomeConditionMatcher;
import org.bugby.matcher.wildcard.expr.SomeConditionUsingMatcher;
import org.bugby.matcher.wildcard.expr.SomeExpressionThrowingMatcher;
import org.bugby.matcher.wildcard.expr.SomeExpressionUsingMatcher;
import org.bugby.matcher.wildcard.expr.SomeValueMatcher;
import org.bugby.matcher.wildcard.method.SomeMethodMatcher;
import org.bugby.matcher.wildcard.var.SomeFieldMatcher;
import org.bugby.matcher.wildcard.var.SomeParamMatcher;
import org.bugby.matcher.wildcard.var.SomeVariableMatcher;

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
	@Pattern(SomeFieldMatcher.class)
	public static SomeType someField;

	/**
	 * matches any parameter
	 */
	@Pattern(SomeParamMatcher.class)
	public static SomeType someParam;

	/**
	 * matches any variable
	 */
	@Pattern(SomeVariableMatcher.class)
	public static SomeType someVar;

	/**
	 * matches any expression of any type
	 * @return
	 */
	@Pattern(SomeValueMatcher.class)
	public static <T> T someValue() {
		return null;
	}

	@Pattern(SomeValueMatcher.class)
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
	@Pattern(SomeExpressionThrowingMatcher.class)
	public static <T extends Exception, V> V someExpressionThrowing(Class<T> clazz) throws T {//
		return null;
	}

	@Pattern(SomeConditionMatcher.class)
	public static boolean someCondition() {
		return true;
	}

	@Pattern(SomeExpressionUsingMatcher.class)
	public static <T> T someExpressionUsing(Object... value) {
		return null;
	}

	@Pattern(SomeConditionUsingMatcher.class)
	public static boolean someConditionUsing(Object... value) {
		return true;
	}

	@Pattern(SomeConditionUsingMatcher.class)
	public static <T> T anywhere(Object... value) {
		return null;
	}

	@Pattern(BeginMatcher.class)
	public static void begin() {
		//
	}

	@Pattern(EndMatcher.class)
	public static void end() {
		//
	}

	@Pattern(NoCodeMatcher.class)
	public static void noCode() {
		//
	}

	@Pattern(SomeMethodMatcher.class)
	public static void someMethod() {
		//
	}

	@Pattern(AnyBranchMatcher.class)
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
	@Pattern(SomeCodeMatcher.class)
	public static void someCode() {//
	}
}
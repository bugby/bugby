package org.bugby.wildcard;

import org.bugby.api.annotation.Pattern;
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

@SuppressWarnings("unused")
public class Wildcards {

	// LITERALS
	public static int someInteger = 0;
	public static long someLong = 0;
	public static boolean someBoolean = true;

	/**
	 * matches any expression of any type
	 * 
	 * @return
	 */
	@Pattern(SomeValueMatcher.class)
	public static <T> T someValue() {
		return null;
	}

	@Pattern(SomeValueMatcher.class)
	public static int someIntValue() {
		return 0;
	}

	@Pattern(SomeValueMatcher.class)
	public static float someFloatValue() {
		return 0;
	}

	@Pattern(SomeValueMatcher.class)
	public static double someDoubleValue() {
		return 0;
	}

	@Pattern(SomeValueMatcher.class)
	public static boolean someBooleanValue() {
		return false;
	}

	@Pattern(SomeValueMatcher.class)
	public static <T> T someTypedValue(Class<T> type) {
		return null;
	}

	/**
	 * matches any expression. The clazz parameter that represents the class of the exception to be thrown is not
	 * checked. It has only the role to allow throwing the desired exception. This is usually used when the pattern
	 * concentrates more on the "catch" part of an block.
	 * 
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

	@Pattern(AnyBranchMatcher.class)
	public static void anyBranch() {

	}

	/**
	 * matches any block of code or an entire method. It can be used as a method definition only, not as a method call!
	 * Example:
	 *
	 * <pre>
	 * public void someCode() {
	 * 	// ... other matchers
	 * }
	 * </pre>
	 */
	@Pattern(SomeCodeMatcher.class)
	public static void someCode() {//
	}
}

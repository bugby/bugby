package org.bugby.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.bugby.matcher.wildcard.code.OrSetMatcher;
import org.bugby.wildcard.Pattern;
import org.bugby.wildcard.PatternConfig;

/**
 *
 * this annotation can be used to specify elements (for example methods) that are part of a set of alternatives, instead by being all required
 *
 * <pre>
 * public class MyPatternClass {
 * 	&#064;OrSet(1)
 * 	public void method1() {
 * 	}
 * 
 * 	&#064;OrSet(1)
 * 	public void method2() {
 * 	}
 * }
 * </pre>
 *
 * In the previous example a source class will match if it will have a method that matches either method1 or method2. If the annotation was
 * missing, the source class would've had to match both method1 and method2.
 *
 * @author acraciun
 */
@Pattern(OrSetMatcher.class)
@PatternConfig
@Retention(RetentionPolicy.RUNTIME)
public @interface OrSet {
	/**
	 * this indicates which set the annotated elements is part of.
	 */
	public int value() default 0;
}

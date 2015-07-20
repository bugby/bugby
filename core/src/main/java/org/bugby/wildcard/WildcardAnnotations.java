package org.bugby.wildcard;

import org.bugby.api.annotation.Missing;
import org.bugby.api.annotation.Pattern;
import org.bugby.api.annotation.PseudoAnnotation;
import org.bugby.matcher.wildcard.code.LastMatcher;

/**
 * this is used to "annotate" statements as this is not possible using regular annotations.
 * 
 * @author acraciun
 *
 */
public class WildcardAnnotations {
	@Pattern(LastMatcher.class)
	public static void $Last() {

	}

	@PseudoAnnotation(Missing.class)
	public static void $Missing() {

	}

	public static void $AnyLoop() {

	}
}

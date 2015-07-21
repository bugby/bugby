package org.bugby.bugs.findbugs.badpractice;

import org.bugby.api.annotation.Correlation;
import org.bugby.api.annotation.Pattern;
import org.bugby.wildcard.correlation.SimilarName;

/**
 *
 * Nm: Very confusing method names (but perhaps intentional) (NM_VERY_CONFUSING_INTENTIONAL) The referenced methods have names that differ only
 * by capitalization. This is very confusing because if the capitalization were identical then one of the methods would override the other. From
 * the existence of other methods, it seems that the existence of both of these methods is intentional, but is sure is confusing. You should try
 * hard to eliminate one of them, unless you are forced to have both due to frozen APIs.
 *
 *
 * @author acraciun
 */
@Pattern
public class NamingVeryConfusingIntentional extends SomeTypeWithSimilarMethod {
	@Correlation(key = "similarName", comparator = SimilarName.class)
	public void method2() {
	}
}

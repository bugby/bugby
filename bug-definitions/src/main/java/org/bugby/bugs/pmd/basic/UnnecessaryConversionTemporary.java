package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;

import org.bugby.api.annotation.Pattern;

/**
 * Avoid unnecessary temporaries when converting primitives to Strings
 * @author acraciun
 */
@Pattern
public class UnnecessaryConversionTemporary {
	public void someCode(int someVar) {
		someExpressionUsing(new Integer(someVar).toString());
	}
}

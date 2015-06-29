package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;

import org.bugby.annotation.BadExample;

/**
 * Avoid unnecessary temporaries when converting primitives to Strings
 * @author acraciun
 */
@BadExample
public class UnnecessaryConversionTemporary {
	public void someCode(int someVar) {
		someExpressionUsing(new Integer(someVar).toString());
	}
}

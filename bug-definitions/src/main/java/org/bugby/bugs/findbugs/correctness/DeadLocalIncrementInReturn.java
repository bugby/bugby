package org.bugby.bugs.findbugs.correctness;

/**
 *
 * DLS: Useless increment in return statement (DLS_DEAD_LOCAL_INCREMENT_IN_RETURN) This statement has a return such as return x++;. A postfix
 * increment/decrement does not impact the value of the expression, so this increment/decrement has no effect. Please verify that this statement
 * does the right thing.
 * 
 * @author acraciun
 */
public class DeadLocalIncrementInReturn {
	public int someCode(int x) {
		return x++;
	}
}

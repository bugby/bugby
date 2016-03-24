package org.bugby.pattern.example.findbugs.correctness;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;

import org.bugby.api.annotation.OrSet;

/**
 *
 * DLS: Overwritten increment (DLS_OVERWRITTEN_INCREMENT) The code performs an increment operation (e.g., i++) and then immediately overwrites
 * it. For example, i = i++ immediately overwrites the incremented value with the original value.
 *
 * @author acraciun
 */
public class OverrittenIncrement {
	@OrSet
	public void someCode1(int i) {
		i = someExpressionUsing(i++);
	}

	@OrSet
	public void someCode2(int i) {
		i = someExpressionUsing(i--);
	}
}

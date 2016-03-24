package org.bugby.bugs.findbugs.correctness;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;

import org.bugby.wildcard.type.SomeInterface;
import org.bugby.wildcard.type.SomeTypeNotImplementing;

/**
 *
 * BC: instanceof will always return false (BC_IMPOSSIBLE_INSTANCEOF) This instanceof test will always return false. Although this is safe, make
 * sure it isn't an indication of some misunderstanding or some other logic error.
 * 
 * 
 * @author acraciun
 */
public class ImpossibleInstanceOf {
	public void someCode(SomeTypeNotImplementing<SomeInterface> x) {
		someExpressionUsing(x instanceof SomeInterface);
	}
}

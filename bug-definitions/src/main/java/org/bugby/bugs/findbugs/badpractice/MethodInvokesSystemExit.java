package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someIntValue;

import org.bugby.api.annotation.Pattern;

/**
 * Dm: Method invokes System.exit(...) (DM_EXIT)
 *
 * Invoking System.exit shuts down the entire Java virtual machine. This should only been done when it is appropriate. Such calls make it hard or
 * impossible for your code to be invoked by other code. Consider throwing a RuntimeException instead.
 *
 * @author acraciun
 */
@Pattern
public class MethodInvokesSystemExit {
	public void someCode() {
		System.exit(someIntValue());
	}
}

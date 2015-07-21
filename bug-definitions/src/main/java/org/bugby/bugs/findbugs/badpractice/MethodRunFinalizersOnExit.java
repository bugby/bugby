package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someBooleanValue;

import org.bugby.api.annotation.OrSet;
import org.bugby.api.annotation.Pattern;

/**
 *
 * Dm: Method invokes dangerous method runFinalizersOnExit (DM_RUN_FINALIZERS_ON_EXIT)
 *
 * Never call System.runFinalizersOnExit or Runtime.runFinalizersOnExit for any reason: they are among the most dangerous methods in the Java
 * libraries. -- Joshua Bloch
 *
 * @author acraciun
 */
@Pattern
public class MethodRunFinalizersOnExit {
	@SuppressWarnings("deprecation")
	@OrSet
	public void someCode1() {
		System.runFinalizersOnExit(someBooleanValue());
	}

	@SuppressWarnings("deprecation")
	@OrSet
	public void someCode2() {
		Runtime.runFinalizersOnExit(someBooleanValue());
	}
}

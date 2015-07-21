package org.bugby.bugs.findbugs.badpractice;

import org.bugby.api.annotation.ModifiersMatching;
import org.bugby.api.annotation.Pattern;

/**
 *
 * IC: Superclass uses subclass during initialization (IC_SUPERCLASS_USES_SUBCLASS_DURING_INITIALIZATION)
 * 
 * During the initialization of a class, the class makes an active use of a subclass. That subclass will not yet be initialized at the time of
 * this use. For example, in the following code, foo will be null.
 * 
 * 
 * @author acraciun
 */
@Pattern
public class CircularClassInitialization {
	@ModifiersMatching(STATIC = true)
	static class InnerClassSingleton extends CircularClassInitialization {
		@ModifiersMatching(STATIC = true)
		static InnerClassSingleton singleton = new InnerClassSingleton();
	}

	@ModifiersMatching(STATIC = true)
	static CircularClassInitialization foo = InnerClassSingleton.singleton;
}
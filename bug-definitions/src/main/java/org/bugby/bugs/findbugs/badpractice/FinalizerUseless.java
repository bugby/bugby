package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.noCode;

import org.bugby.api.annotation.Pattern;

/**
 *
 * FI: Finalizer does nothing but call superclass finalizer (FI_USELESS)
 * 
 * The only thing this finalize() method does is call the superclass's finalize() method, making it redundant. Delete it.
 * 
 * @author acraciun
 */
@Pattern
public class FinalizerUseless extends SomeTypeWithFinalize {
	@Override
	public void finalize() {
		noCode();
		super.finalize();
		noCode();
	}
}

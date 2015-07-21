package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.WildcardAnnotations.$Missing;

import org.bugby.api.annotation.Pattern;

/**
 *
 * FI: Finalizer does not call superclass finalizer (FI_MISSING_SUPER_CALL)
 *
 * This finalize() method does not make a call to its superclass's finalize() method. So, any finalizer actions defined for the superclass will
 * not be performed. Add a call to super.finalize().
 *
 * detects also FI_NULLIFY_SUPER
 *
 * @author acraciun
 */
@Pattern
public class FinalizerMissingSuperCall extends SomeTypeWithFinalize {
	@Override
	public void finalize() {
		$Missing();
		super.finalize();
	}
}

package org.bugby.bugs.findbugs.badpractice;

import org.bugby.api.annotation.Pattern;

/**
 *
 * FI: Finalizer nulls fields (FI_FINALIZER_NULLS_FIELDS)
 *
 * This finalizer nulls out fields. This is usually an error, as it does not aid garbage collection, and the object is going to be garbage
 * collected anyway.
 *
 * it detects also FI_FINALIZER_ONLY_NULLS_FIELDS
 *
 * @author acraciun
 */
@Pattern
public class FinalizerNullsFields {
	private Object field;

	@Override
	public void finalize() {
		field = null;
	}
}

package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.noCode;

import org.bugby.api.annotation.Pattern;

/**
 *
 * FI: Empty finalizer should be deleted (FI_EMPTY)
 *
 * Empty finalize() methods are useless, so they should be deleted.
 *
 * @author acraciun
 */
@Pattern
public class FinalizerEmpty {
	@Override
	public void finalize() {
		noCode();
	}
}

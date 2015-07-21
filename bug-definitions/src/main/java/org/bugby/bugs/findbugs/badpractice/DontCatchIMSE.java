package org.bugby.bugs.findbugs.badpractice;

import org.bugby.api.annotation.Pattern;

/**
 *
 * IMSE: Dubious catching of IllegalMonitorStateException (IMSE_DONT_CATCH_IMSE)
 *
 * IllegalMonitorStateException is generally only thrown in case of a design flaw in your code (calling wait or notify on an object you do not
 * hold a lock on).
 *
 * @author acraciun
 */
@Pattern
public class DontCatchIMSE {
	public void someCode() {
		try {
		}
		catch (IllegalMonitorStateException ex) {
		}
	}
}

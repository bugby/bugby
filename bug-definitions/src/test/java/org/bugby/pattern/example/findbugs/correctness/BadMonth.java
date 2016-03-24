package org.bugby.pattern.example.findbugs.correctness;

import static org.bugby.wildcard.Wildcards.someIntValueOutside;

import java.util.Calendar;

/**
 *
 * DMI: Bad constant value for month (DMI_BAD_MONTH) This code passes a constant month value outside the expected range of 0..11 to a method.
 *
 * @author acraciun
 */
public class BadMonth {
	public void someCode(Calendar c) {
		c.set(Calendar.MONTH, someIntValueOutside(0, 11));
	}
	//TODO other cases - constructors !?
}
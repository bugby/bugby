package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;

import java.util.Random;

import org.bugby.wildcard.Pattern;

/**
 * DMI: Random object created and used only once (DMI_RANDOM_USED_ONLY_ONCE)
 *
 * This code creates a java.util.Random object, uses it to generate one random number, and then discards the Random object. This produces
 * mediocre quality random numbers and is inefficient. If possible, rewrite the code so that the Random object is created once and saved, and
 * each time a new random number is required invoke a method on the existing Random object to obtain it.
 *
 * If it is important that the generated Random numbers not be guessable, you must not create a new Random for each random number; the values are
 * too easily guessable. You should strongly consider using a java.security.SecureRandom instead (and avoid allocating a new SecureRandom for
 * each random number needed).
 *
 *
 * @author acraciun
 */
@Pattern
public class RandomUsedOnlyOnce {
	public void someCode() {
		Random r = new Random();
		someExpressionUsing(r.nextDouble());
		//TODO check this is accurate
		//TODO do the same for all nextXXX methods
	}
}

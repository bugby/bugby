package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;
import static org.bugby.wildcard.Wildcards.someIntValue;
import static org.bugby.wildcard.Wildcards.someTypedValue;

import java.util.Random;

import org.bugby.api.annotation.OrSet;
import org.bugby.api.annotation.Pattern;

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
	@OrSet
	public void someCode1() {
		Random r = new Random();
		someExpressionUsing(r.nextDouble());
	}

	@OrSet
	public void someCode2() {
		someExpressionUsing(new Random().nextDouble());
	}

	@OrSet
	public void someCode3() {
		Random r = new Random();
		someExpressionUsing(r.nextInt());
	}

	@OrSet
	public void someCode4() {
		someExpressionUsing(new Random().nextInt());
	}

	@OrSet
	public void someCode5() {
		Random r = new Random();
		someExpressionUsing(r.nextInt(someIntValue()));
	}

	@OrSet
	public void someCode6() {
		someExpressionUsing(new Random().nextInt(someIntValue()));
	}

	@OrSet
	public void someCode7() {
		Random r = new Random();
		someExpressionUsing(r.nextBoolean());
	}

	@OrSet
	public void someCode8() {
		someExpressionUsing(new Random().nextBoolean());
	}

	@OrSet
	public void someCode9() {
		Random r = new Random();
		someExpressionUsing(r.nextFloat());
	}

	@OrSet
	public void someCode10() {
		someExpressionUsing(new Random().nextFloat());
	}

	@OrSet
	public void someCode11() {
		Random r = new Random();
		someExpressionUsing(r.nextLong());
	}

	@OrSet
	public void someCode12() {
		someExpressionUsing(new Random().nextLong());
	}

	@OrSet
	public void someCode13() {
		Random r = new Random();
		r.nextBytes(someTypedValue(byte[].class));
	}

	@OrSet
	public void someCode14() {
		new Random().nextBytes(someTypedValue(byte[].class));
	}
}

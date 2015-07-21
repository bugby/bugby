package org.bugby.bugs.findbugs.badpractice;

import javax.annotation.concurrent.Immutable;

import org.bugby.api.annotation.ModifiersMatching;
import org.bugby.api.annotation.Pattern;

/**
 *
 * JCIP: Fields of immutable classes should be final (JCIP_FIELD_ISNT_FINAL_IN_IMMUTABLE_CLASS) The class is annotated with
 * net.jcip.annotations.Immutable or javax.annotation.concurrent.Immutable, and the rules for those annotations require that all fields are
 * final.
 *
 * @author acraciun
 */
@Pattern
@Immutable
public class CheckImmutable {
	@ModifiersMatching(FINAL = true)
	private Object someField;

}

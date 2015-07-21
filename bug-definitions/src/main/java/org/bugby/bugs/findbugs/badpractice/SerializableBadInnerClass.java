package org.bugby.bugs.findbugs.badpractice;

import java.io.Serializable;

import org.bugby.api.annotation.ModifiersMatching;
import org.bugby.api.annotation.Pattern;
import org.bugby.wildcard.type.MissingInterface;

/**
 *
 * Se: Non-serializable class has a serializable inner class (SE_BAD_FIELD_INNER_CLASS) This Serializable class is an inner class of a
 * non-serializable class. Thus, attempts to serialize it will also attempt to associate instance of the outer class with which it is associated,
 * leading to a runtime error. If possible, making the inner class a static inner class should solve the problem. Making the outer class
 * serializable might also work, but that would mean serializing an instance of the inner class would always also serialize the instance of the
 * outer class, which it often not what you really want.
 *
 * @author acraciun
 */
@Pattern
public class SerializableBadInnerClass implements MissingInterface<Serializable> {
	@ModifiersMatching(STATIC = true)
	public class InnerClass implements Serializable {
	}
}

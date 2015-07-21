package org.bugby.bugs.findbugs.badpractice;

import java.io.Serializable;

import org.bugby.api.annotation.ModifiersMatching;
import org.bugby.api.annotation.Pattern;

/**
 *
 * Se: Serializable inner class (SE_INNER_CLASS) This Serializable class is an inner class. Any attempt to serialize it will also serialize the
 * associated outer instance. The outer instance is serializable, so this won't fail, but it might serialize a lot more data than intended. If
 * possible, making the inner class a static inner class (also known as a nested class) should solve the problem.
 *
 * @author acraciun
 */
@Pattern
public class SerializableInnerClass implements Serializable {
	@ModifiersMatching(STATIC = true)
	public class InnerClass implements Serializable {
	}
}
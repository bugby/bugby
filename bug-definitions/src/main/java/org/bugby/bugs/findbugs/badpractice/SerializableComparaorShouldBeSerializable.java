package org.bugby.bugs.findbugs.badpractice;

import java.io.Serializable;
import java.util.Comparator;

import org.bugby.api.annotation.Pattern;
import org.bugby.wildcard.type.MissingInterface;

/**
 *
 * Se: Comparator doesn't implement Serializable (SE_COMPARATOR_SHOULD_BE_SERIALIZABLE) This class implements the Comparator interface. You
 * should consider whether or not it should also implement the Serializable interface. If a comparator is used to construct an ordered collection
 * such as a TreeMap, then the TreeMapwill be serializable only if the comparator is also serializable. As most comparators have little or no
 * state, making them serializable is generally easy and good defensive programming.
 * 
 * @author acraciun
 */
@Pattern
public abstract class SerializableComparaorShouldBeSerializable implements Comparator<Object>, MissingInterface<Serializable> {
}

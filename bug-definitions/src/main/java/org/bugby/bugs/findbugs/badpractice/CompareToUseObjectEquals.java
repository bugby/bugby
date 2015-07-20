package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someBooleanValue;
import static org.bugby.wildcard.Wildcards.someIntValue;

import org.bugby.api.annotation.Missing;
import org.bugby.api.annotation.Pattern;

/**
 *
 * Eq: Class defines compareTo(...) and uses Object.equals() (EQ_COMPARETO_USE_OBJECT_EQUALS)
 * 
 * This class defines a compareTo(...) method but inherits its equals() method from java.lang.Object. Generally, the value of compareTo should
 * return zero if and only if equals returns true. If this is violated, weird and unpredictable failures will occur in classes such as
 * PriorityQueue. In Java 5 the PriorityQueue.remove method uses the compareTo method, while in Java 6 it uses the equals method.
 * 
 * From the JavaDoc for the compareTo method in the Comparable interface:
 * 
 * It is strongly recommended, but not strictly required that (x.compareTo(y)==0) == (x.equals(y)). Generally speaking, any class that implements
 * the Comparable interface and violates this condition should clearly indicate this fact. The recommended language is
 * "Note: this class has a natural ordering that is inconsistent with equals."
 * @author acraciun
 */
@Pattern
public class CompareToUseObjectEquals implements Comparable<Object> {
	@Override
	public int compareTo(Object obj) {
		return someIntValue();
	}

	@Override
	@Missing
	public boolean equals(Object obj) {
		return someBooleanValue();
	}
}

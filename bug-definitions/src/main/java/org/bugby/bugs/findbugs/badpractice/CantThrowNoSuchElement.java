package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.WildcardAnnotations.$Missing;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.bugby.api.annotation.Pattern;

/**
 *
 * It: Iterator next() method can't throw NoSuchElementException (IT_NO_SUCH_ELEMENT)
 * 
 * This class implements the java.util.Iterator interface. However, its next() method is not capable of throwing
 * java.util.NoSuchElementException. The next() method should be changed so it throws NoSuchElementException if is called when there are no more
 * elements to return.
 * 
 * 
 * @author acraciun
 */
@Pattern
abstract public class CantThrowNoSuchElement implements Iterator<Object> {
	@Override
	public Object next() {
		$Missing();
		throw new NoSuchElementException();
	}
}

package org.bugby.pattern.example.findbugs.correctness;

import static org.bugby.wildcard.Wildcards.someBooleanValue;
import static org.bugby.wildcard.Wildcards.someExpressionUsing;

import java.util.Iterator;

/**
 *
 * DMI: hasNext method invokes next (DMI_CALLING_NEXT_FROM_HASNEXT) The hasNext() method invokes the next() method. This is almost certainly
 * wrong, since the hasNext() method is not supposed to change the state of the iterator, and the next method is supposed to change the state of
 * the iterator.
 *
 * @author acraciun
 */
abstract public class CallingNextFromHasNext implements Iterator {
	public boolean hasNext() {
		someExpressionUsing(next());
		//TODO comparent when next() is in return as well
		return someBooleanValue();
	}
}

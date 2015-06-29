package org.bugby.bugs.pmd.basic;

import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.annotation.BadExample;
import org.bugby.wildcard.SomeType;

/**
 * Partially created objects can be returned by the Double Checked Locking pattern when used in Java. An optimizing JRE
 * may assign a reference to the baz variable before it creates the object the reference is intended to point to. For
 * more details see http://www.javaworld.com/javaworld/jw-02-2001/jw-0209-double.html.
 * 
 * @author acraciun
 */
@BadExample
public class DoubleCheckedLocking {
	SomeType someField;

	public SomeType someMethod() {
		if (someField == null) { // someField may be non-null yet not fully created
			synchronized (this) {
				if (someField == null) {
					someField = someValue();
				}
			}
		}
		return someField;
	}
}

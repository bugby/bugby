package org.bugby.bugs.findbugs;

import static org.bugby.wildcard.Wildcards.someTypedValue;

import java.util.Collection;

import org.bugby.annotation.BadExample;
import org.bugby.annotation.IgnoreFromMatching;
import org.bugby.wildcard.SomeType;

@BadExample
public class CollectionRemoveAll {
	public void someCode() {
		// t can also be field, variable, parameter etc .... so the initialization itself is not part of the match, only
		// the type
		@IgnoreFromMatching
		Collection<SomeType> c = someTypedValue(Collection.class);

		c.removeAll(c);
	}
}

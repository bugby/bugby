package org.bugby.bugs.findbugs;

import java.util.Collection;

import org.bugby.annotation.BadExample;

@BadExample
public class CollectionRemoveAll {
	public void someCode(Collection<?> someVar) {
		// someVar can also be field, variable, parameter etc

		someVar.removeAll(someVar);
	}
}

package org.bugby.pattern.example.findbugs;

import java.util.Arrays;
import java.util.Collection;

public class CollectionRemoveAllCheck2 {
	public void someCode() {
		// t can also be field, variable, parameter etc .... so the initialization itself is not part of the match, only
		// the type
		Collection<String> c = Arrays.asList("a", "b");
		Collection<String> d = Arrays.asList("a");

		c.removeAll(d);
	}
}

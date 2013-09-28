package org.bugby.pattern.example.findbugs;

import java.util.Collection;

public class CollectionRemoveAllCheck3 {
	public void myMethod(Collection<String> c) {

		c.removeAll(c);
	}
}

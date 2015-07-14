package org.bugby.pattern.example.findbugs;

public class CollectionRemoveAllCheck4 {
	public void removeAll(CollectionRemoveAllCheck4 x) {

	}

	public void myMethod() {
		// t can also be field, variable, parameter etc .... so the initialization itself is not part of the match, only
		// the type
		CollectionRemoveAllCheck4 m = new CollectionRemoveAllCheck4();
		m.removeAll(m);
	}
}

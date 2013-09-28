package org.bugby.pattern.example.findbugs;

public class CollectionRemoveAllCheck4 {
	public class MyCollection {
		public void removeAll(MyCollection x) {

		}
	}

	public void myMethod() {
		// t can also be field, variable, parameter etc .... so the initialization itself is not part of the match, only
		// the type
		MyCollection m = new MyCollection();
		m.removeAll(m);
	}
}

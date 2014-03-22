package org.bugby.bugs.findbugs;

import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.annotation.GoodExample;

@GoodExample
public class CloneableWithoutClone implements Cloneable {
	@Override
	public Object clone() {
		return someValue();
	}
}

package org.bugby.bugs.findbugs;

import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.annotation.GoodExample;

@GoodExample
public class ClonableWithoutClone implements Cloneable {
	@Override
	public ClonableWithoutClone clone() {
		return someValue();
	}
}

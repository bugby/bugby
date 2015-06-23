package org.bugby.bugs.findbugs;

import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.annotation.BadExample;
import org.bugby.wildcard.Missing;

@BadExample
public class CloneableWithoutClone implements Cloneable {
	@Override
	@Missing
	public Object clone() {
		return someValue();
	}
}

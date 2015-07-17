package org.bugby.bugs.findbugs;

import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.wildcard.Missing;
import org.bugby.wildcard.Pattern;

@Pattern
public class CloneableWithoutClone implements Cloneable {
	@Override
	@Missing
	public Object clone() {
		return someValue();
	}
}

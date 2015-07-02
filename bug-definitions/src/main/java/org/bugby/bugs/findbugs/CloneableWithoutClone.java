package org.bugby.bugs.findbugs;

import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.api.Pattern;
import org.bugby.wildcard.Missing;

@Pattern
public class CloneableWithoutClone implements Cloneable {
	@Override
	@Missing
	public Object clone() {
		return someValue();
	}
}

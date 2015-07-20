package org.bugby.bugs.findbugs;

import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.api.annotation.Missing;
import org.bugby.api.annotation.Pattern;

@Pattern
public class CloneableWithoutClone implements Cloneable {
	@Override
	@Missing
	public Object clone() {
		return someValue();
	}
}

package org.bugby.pattern.example.findbugs.correctness;

import java.util.Collection;

public class ImpossibleDowncastToArrayNoBug {
	public Object[] getAsArray(Collection<String> c) {
		return c.toArray();
	}
}

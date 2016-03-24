package org.bugby.pattern.example.findbugs.correctness;

import java.util.Collection;

public class ImpossibleDowncastToArrayBug {
	public String[] getAsArray(Collection<String> c) {
		return (String[]) c.toArray();
	}
}

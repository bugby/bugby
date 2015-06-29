package org.bugby.bugs.pmd.basic;

import org.bugby.annotation.BadExample;
import org.bugby.wildcard.Wildcards;

@BadExample
public class EmptySwitchStatements {
	public void someCode() {
		switch (Wildcards.<Integer> someValue()) {
		//TODO FIX it
		//noCode();
		}
	}
}

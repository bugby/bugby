package org.bugby.bugs.pmd.basic;

import org.bugby.wildcard.Pattern;
import org.bugby.wildcard.Wildcards;

@Pattern
public class EmptySwitchStatements {
	public void someCode() {
		switch (Wildcards.<Integer> someValue()) {
		//TODO FIX it
		//noCode();
		}
	}
}

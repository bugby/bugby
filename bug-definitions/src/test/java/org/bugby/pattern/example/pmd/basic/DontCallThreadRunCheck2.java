package org.bugby.pattern.example.pmd.basic;

import org.bugby.wildcard.Pattern;

@Pattern
public class DontCallThreadRunCheck2 {
	public void someCode(Thread th) {
		// does not work in PMD
		th.run();
	}
}

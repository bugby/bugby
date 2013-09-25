package org.bugby.pattern.example.pmd;

import org.bugby.annotation.BadExample;

@BadExample
public class DontCallThreadRunCheck2 {
	public void someCode(Thread th) {
		// does not work in PMD
		th.run();
	}
}

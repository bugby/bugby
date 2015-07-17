package org.bugby.pattern.example.pmd.basic;

import org.bugby.wildcard.Pattern;

@Pattern
public class DontCallThreadRunCheck1 {
	public void someCode() {
		Thread th = new Thread();
		th.run();
	}
}

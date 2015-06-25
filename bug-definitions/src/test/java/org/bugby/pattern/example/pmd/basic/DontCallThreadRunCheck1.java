package org.bugby.pattern.example.pmd.basic;

import org.bugby.annotation.BadExample;

@BadExample
public class DontCallThreadRunCheck1 {
	public void someCode() {
		Thread th = new Thread();
		th.run();
	}
}

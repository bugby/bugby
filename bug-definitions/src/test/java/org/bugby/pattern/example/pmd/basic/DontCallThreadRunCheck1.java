package org.bugby.pattern.example.pmd.basic;

import org.bugby.api.annotation.Pattern;

@Pattern
public class DontCallThreadRunCheck1 {
	public void someCode() {
		Thread th = new Thread();
		th.run();
	}
}

package org.bugby.pattern.example.pmd;

import java.util.Collections;
import java.util.List;

import org.bugby.annotation.BadExample;

@BadExample
public class DontCallThreadRunCheck3 {
	public void someCode() {
		// does not work in PMD
		List<Thread> threads = Collections.singletonList(new Thread());
		threads.get(0).run();
	}
}

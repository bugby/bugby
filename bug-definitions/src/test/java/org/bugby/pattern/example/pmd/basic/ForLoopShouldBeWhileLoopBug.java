package org.bugby.pattern.example.pmd.basic;

public class ForLoopShouldBeWhileLoopBug {
	void bar() {
		for (; true;) { // No Init or Update part, may as well be: while (true)
		}
	}
}

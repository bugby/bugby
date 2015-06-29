package org.bugby.pattern.example.pmd.basic;

public class ForLoopShouldBeWhileLoopNoBug {
	void bar() {
		for (int i = 0; true;) { // No Init or Update part, may as well be: while (true)
		}
	}
}

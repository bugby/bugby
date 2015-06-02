package org.bugby.pattern.example.pmd;

/**
 * this is a bad code: @GoodExampleTrigger should match, @GoodExample not
 * 
 * @author acraciun
 * 
 */
public class AvoidBranchingStatementAsLastInLoopCheck1 {
	public void myMethod() {
		int n = 5;
		for (int j = 0; j < n; ++j) {
			System.out.println("this:" + j);
			if (j < 10) {
				continue;
			}
			System.out.println("more");
			break;
		}
	}
}

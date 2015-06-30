package org.bugby.pattern.example.pmd;

import static org.bugby.pattern.example.MatchingHelper.assertBug;

import org.bugby.bugs.pmd.AvoidBranchingStatementAsLastInLoop;
import org.junit.Test;

public class TestPmd {
	@Test
	public void testAvoidBranchingStatement1() {
		assertBug(AvoidBranchingStatementAsLastInLoop.class, AvoidBranchingStatementAsLastInLoopCheck1.class, 18);
	}

	@Test
	public void testAvoidBranchingStatement2() {
		assertBug(AvoidBranchingStatementAsLastInLoop.class, AvoidBranchingStatementAsLastInLoopCheck2.class, 18);
	}

}

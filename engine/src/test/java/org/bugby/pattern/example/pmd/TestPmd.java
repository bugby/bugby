package org.bugby.pattern.example.pmd;

import static org.bugby.pattern.example.MatchingHelper.assertPmd;

import org.junit.Test;

public class TestPmd {
	@Test
	public void testAvoidBranchingStatement() {
		assertPmd("AvoidBranchingStatementAsLastInLoop.java", "AvoidBranchingStatementAsLastInLoopCheck2.java", 18);
	}

	@Test
	public void testCollapsibleIfStatement1() {
		assertPmd("CollapsibleIfStatements.java", "CollapsibleIfStatementsCheck.java", 14);
	}
}

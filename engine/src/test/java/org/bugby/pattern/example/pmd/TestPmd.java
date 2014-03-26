package org.bugby.pattern.example.pmd;

import static org.bugby.pattern.example.MatchingHelper.assertPmd;
import static org.bugby.pattern.example.MatchingHelper.assertPmdNotMatch;

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

	@Test
	public void testCollapsibleIfStatement2() {
		assertPmdNotMatch("CollapsibleIfStatements.java", "CollapsibleIfStatementsCheck2.java");
	}

	@Test
	public void testCollapsibleIfStatement3() {
		assertPmdNotMatch("CollapsibleIfStatements.java", "CollapsibleIfStatementsCheck3.java");
	}

	@Test
	public void testCollapsibleIfStatement4() {
		assertPmd("CollapsibleIfStatements.java", "CollapsibleIfStatementsCheck4.java", 25);
	}

	@Test
	public void testDontCallThreadRun1() {
		assertPmd("DontCallThreadRun.java", "DontCallThreadRunCheck1.java", 9);
	}

	@Test
	public void testDontCallThreadRun2() {
		assertPmd("DontCallThreadRun.java", "DontCallThreadRunCheck2.java", 9);
	}

	@Test
	public void testDontCallThreadRun3() {
		assertPmd("DontCallThreadRun.java", "DontCallThreadRunCheck3.java", 13);
	}
}

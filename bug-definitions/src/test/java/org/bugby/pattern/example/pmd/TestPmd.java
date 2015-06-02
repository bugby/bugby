package org.bugby.pattern.example.pmd;

import static org.bugby.pattern.example.MatchingHelper.assertPmd;
import static org.bugby.pattern.example.MatchingHelper.assertPmdNotMatch;

import org.junit.Test;

public class TestPmd {
	@Test
	public void testAvoidBranchingStatement1() {
		assertPmd("AvoidBranchingStatementAsLastInLoop.java", "AvoidBranchingStatementAsLastInLoopCheck1.java", 18);
	}

	@Test
	public void testAvoidBranchingStatement2() {
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

	@Test
	public void testJumbledIncrementer1() {
		assertPmd("JumbledIncrementer.java", "JumbledIncrementerCheck1.java", 6);
	}

	@Test
	public void testJumbledIncrementer2() {
		assertPmdNotMatch("JumbledIncrementer.java", "JumbledIncrementerCheck2.java");
	}

	@Test
	public void testJumbledIncrementer3() {
		assertPmd("JumbledIncrementer.java", "JumbledIncrementerCheck3.java", 6);
	}

	@Test
	public void testMisplacedNullCheckAnd1() {
		assertPmd("MisplacedNullCheckAnd.java", "MisplacedNullCheckAndCheck1.java", 8);
	}

	@Test
	public void testMisplacedNullCheckAnd2() {
		assertPmdNotMatch("MisplacedNullCheckAnd.java", "MisplacedNullCheckAndCheck2.java");
	}

	@Test
	public void testMisplacedNullCheckOr1() {
		assertPmd("MisplacedNullCheckOr.java", "MisplacedNullCheckOrCheck1.java", 7);
	}

	@Test
	public void testOverrideBothEqualsAndHashcode1() {
		assertPmd("OverrideBothEqualsAndHashcode.java", "OverrideBothEqualsAndHashcodeCheck1.java", 54);
	}

	@Test
	public void testOverrideBothEqualsAndHashcode2() {
		assertPmdNotMatch("OverrideBothEqualsAndHashcode.java", "OverrideBothEqualsAndHashcodeCheck2.java");
	}
}

package org.bugby.pattern.example.pmd;

import static org.bugby.pattern.example.MatchingHelper.assertBug;
import static org.bugby.pattern.example.MatchingHelper.assertNoBug;

import org.bugby.bugs.pmd.AvoidBranchingStatementAsLastInLoop;
import org.bugby.bugs.pmd.CollapsibleIfStatements;
import org.bugby.bugs.pmd.DontCallThreadRun;
import org.bugby.bugs.pmd.JumbledIncrementer;
import org.bugby.bugs.pmd.MisplacedNullCheckAnd;
import org.bugby.bugs.pmd.MisplacedNullCheckOr;
import org.bugby.bugs.pmd.OverrideBothEqualsAndHashcode;
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

	@Test
	public void testCollapsibleIfStatement1() {
		assertBug(CollapsibleIfStatements.class, CollapsibleIfStatementsCheck.class, 14);
	}

	@Test
	public void testCollapsibleIfStatement2() {
		assertNoBug(CollapsibleIfStatements.class, CollapsibleIfStatementsCheck2.class);
	}

	@Test
	public void testCollapsibleIfStatement3() {
		assertNoBug(CollapsibleIfStatements.class, CollapsibleIfStatementsCheck3.class);
	}

	@Test
	public void testCollapsibleIfStatement4() {
		assertBug(CollapsibleIfStatements.class, CollapsibleIfStatementsCheck4.class, 25);
	}

	@Test
	public void testDontCallThreadRun1() {
		assertBug(DontCallThreadRun.class, DontCallThreadRunCheck1.class, 9);
	}

	@Test
	public void testDontCallThreadRun2() {
		assertBug(DontCallThreadRun.class, DontCallThreadRunCheck2.class, 9);
	}

	@Test
	public void testDontCallThreadRun3() {
		assertBug(DontCallThreadRun.class, DontCallThreadRunCheck3.class, 13);
	}

	@Test
	public void testJumbledIncrementer1() {
		assertBug(JumbledIncrementer.class, JumbledIncrementerCheck1.class, 6);
	}

	@Test
	public void testJumbledIncrementer2() {
		assertNoBug(JumbledIncrementer.class, JumbledIncrementerCheck2.class);
	}

	@Test
	public void testJumbledIncrementer3() {
		assertBug(JumbledIncrementer.class, JumbledIncrementerCheck3.class, 6);
	}

	@Test
	public void testMisplacedNullCheckAnd1() {
		assertBug(MisplacedNullCheckAnd.class, MisplacedNullCheckAndCheck1.class, 8);
	}

	@Test
	public void testMisplacedNullCheckAnd2() {
		assertNoBug(MisplacedNullCheckAnd.class, MisplacedNullCheckAndCheck2.class);
	}

	@Test
	public void testMisplacedNullCheckOr1() {
		assertBug(MisplacedNullCheckOr.class, MisplacedNullCheckOrCheck1.class, 6);
	}

	@Test
	public void testOverrideBothEqualsAndHashcode1() {
		assertBug(OverrideBothEqualsAndHashcode.class, OverrideBothEqualsAndHashcodeCheck1.class, 54);
	}

	@Test
	public void testOverrideBothEqualsAndHashcode2() {
		assertNoBug(OverrideBothEqualsAndHashcode.class, OverrideBothEqualsAndHashcodeCheck2.class);
	}
}

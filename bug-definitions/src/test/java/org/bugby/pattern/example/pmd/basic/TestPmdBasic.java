package org.bugby.pattern.example.pmd.basic;

import static org.bugby.pattern.example.MatchingHelper.assertBug;
import static org.bugby.pattern.example.MatchingHelper.assertNoBug;

import org.bugby.bugs.pmd.basic.CollapsibleIfStatements;
import org.bugby.bugs.pmd.basic.DontCallThreadRun;
import org.bugby.bugs.pmd.basic.DoubleCheckedLocking;
import org.bugby.bugs.pmd.basic.EmptyCatchBlock;
import org.bugby.bugs.pmd.basic.EmptyFinallyBlock;
import org.bugby.bugs.pmd.basic.EmptyIfStmt;
import org.bugby.bugs.pmd.basic.EmptyTryBlock;
import org.bugby.bugs.pmd.basic.EmptyWhileStmt;
import org.bugby.bugs.pmd.basic.ForLoopShouldBeWhileLoop;
import org.bugby.bugs.pmd.basic.JumbledIncrementer;
import org.bugby.bugs.pmd.basic.MisplacedNullCheckAnd;
import org.bugby.bugs.pmd.basic.MisplacedNullCheckOr;
import org.bugby.bugs.pmd.basic.OverrideBothEqualsAndHashcode1;
import org.bugby.bugs.pmd.basic.OverrideBothEqualsAndHashcode2;
import org.bugby.bugs.pmd.basic.UnnecessaryConversionTemporary;
import org.junit.Test;

public class TestPmdBasic {
	@Test
	public void testEmptyCatchBlockBug() {
		assertBug(EmptyCatchBlock.class, EmptyCatchBlockBug.class, 11);
	}

	@Test
	public void testEmptyCatchBlockNoBug() {
		assertNoBug(EmptyCatchBlock.class, EmptyCatchBlockNoBug.class);
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
	public void testEmptyIfStmtBug() {
		assertBug(EmptyIfStmt.class, EmptyIfStmtBug.class, 6);
	}

	@Test
	public void testEmptyIfStmtNoBug() {
		assertNoBug(EmptyIfStmt.class, EmptyIfStmtNoBug.class);
	}

	@Test
	public void testWhileStmtBug() {
		assertBug(EmptyWhileStmt.class, EmptyWhileStmtBug.class, 5);
	}

	@Test
	public void testWhileStmtNoBug() {
		assertNoBug(EmptyWhileStmt.class, EmptyWhileStmtNoBug.class);
	}

	@Test
	public void testTryBlockBug() {
		assertBug(EmptyTryBlock.class, EmptyTryBlockBug.class, 7);
	}

	@Test
	public void testTryBlockNoBug() {
		assertNoBug(EmptyTryBlock.class, EmptyTryBlockNoBug.class);
	}

	@Test
	public void testTryFinallyBug() {
		assertBug(EmptyFinallyBlock.class, EmptyFinallyBlockBug.class, 8);
	}

	@Test
	public void testTryFinallyNoBug() {
		assertNoBug(EmptyFinallyBlock.class, EmptyFinallyBlockNoBug.class);
	}

	@Test
	public void testForLoopShouldBeWhileLoopBug() {
		assertBug(ForLoopShouldBeWhileLoop.class, ForLoopShouldBeWhileLoopBug.class, 5);
	}

	@Test
	public void testForLoopShouldBeWhileLoopNoBug() {
		assertNoBug(ForLoopShouldBeWhileLoop.class, ForLoopShouldBeWhileLoopNoBug.class);
	}

	@Test
	public void testUnnecessaryConversionTemporaryLoopBug() {
		assertBug(UnnecessaryConversionTemporary.class, UnnecessaryConversionTemporaryBug.class, 6);
	}

	@Test
	public void testUnnecessaryConversionTemporaryNoBug() {
		assertNoBug(UnnecessaryConversionTemporary.class, UnnecessaryConversionTemporaryNoBug.class);
	}

	@Test
	public void testOverrideBothEqualsAndHashcodeBug1() {
		//XXX what line number is correct !?
		assertBug(OverrideBothEqualsAndHashcode1.class, OverrideBothEqualsAndHashcodeBug1.class, 45);
	}

	@Test
	public void testOverrideBothEqualsAndHashcodeBug2() {
		//XXX what line number is correct !?
		assertBug(OverrideBothEqualsAndHashcode2.class, OverrideBothEqualsAndHashcodeBug2.class, 19);
	}

	@Test
	public void testOverrideBothEqualsAndHashcodeNoBug() {
		assertNoBug(OverrideBothEqualsAndHashcode1.class, OverrideBothEqualsAndHashcodeNoBug.class);
		assertNoBug(OverrideBothEqualsAndHashcode2.class, OverrideBothEqualsAndHashcodeNoBug.class);
	}

	@Test
	public void testDoubleCheckedLockingBug() {
		assertBug(DoubleCheckedLocking.class, DoubleCheckedLockingBug.class, 14);
	}

	@Test
	public void testDoubleCheckedLockingNoBug() {
		assertNoBug(DoubleCheckedLocking.class, DoubleCheckedLockingNoBug.class);
	}
}

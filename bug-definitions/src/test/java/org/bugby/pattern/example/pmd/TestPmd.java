package org.bugby.pattern.example.pmd;

import static org.bugby.pattern.example.MatchingHelper.assertBug;
import static org.bugby.pattern.example.MatchingHelper.assertNoBug;

import org.bugby.bugs.pmd.AvoidBranchingStatementAsLastInLoop;
import org.bugby.bugs.pmd.basic.OverrideBothEqualsAndHashcode1;
import org.bugby.pattern.example.pmd.basic.OverrideBothEqualsAndHashcodeNoBug;
import org.bugby.pattern.example.pmd.basic.OverrideBothEqualsAndHashcodeBug1;
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
	public void testOverrideBothEqualsAndHashcode1() {
		assertBug(OverrideBothEqualsAndHashcode1.class, OverrideBothEqualsAndHashcodeNoBug.class, 54);
	}

	@Test
	public void testOverrideBothEqualsAndHashcode2() {
		assertNoBug(OverrideBothEqualsAndHashcode1.class, OverrideBothEqualsAndHashcodeBug1.class);
	}
}

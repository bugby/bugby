package org.bugby.pattern.example.findbugs.correctness;

import static org.bugby.pattern.example.MatchingHelper.assertBug;
import static org.bugby.pattern.example.MatchingHelper.assertNoBug;

import org.bugby.bugs.findbugs.correctness.ImpossibleDowncastToArray;
import org.junit.Test;

public class TestFindbugsCorrectness {

	@Test
	public void testImpossibleDowdcastToArrayBug() {
		assertBug(ImpossibleDowncastToArray.class, ImpossibleDowncastToArrayBug.class, 7);
	}

	@Test
	public void testImpossibleDowdcastToArrayNoBug() {
		assertNoBug(ImpossibleDowncastToArray.class, ImpossibleDowncastToArrayNoBug.class);
	}
}

package org.bugby.pattern.example.findbugs.badpractice;

import static org.bugby.pattern.example.MatchingHelper.assertBug;
import static org.bugby.pattern.example.MatchingHelper.assertNoBug;

import org.bugby.bugs.findbugs.badpractice.CloneNoSuperCall1;
import org.junit.Test;

public class TestFindbugsPadPractice {
	@Test
	public void testCloneNoSuperCallBug() {
		assertBug(CloneNoSuperCall1.class, CloneNoSuperCallBug.class, 5);
	}

	@Test
	public void testCloneNoSuperCallNoBug() {
		assertNoBug(CloneNoSuperCall1.class, CloneNoSuperCallNoBug.class);
	}
}

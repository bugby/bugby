package org.bugby.pattern.example.test;

import static org.bugby.pattern.example.MatchingHelper.assertBug;
import static org.bugby.pattern.example.MatchingHelper.assertNoBug;

import org.junit.Test;

public class TestMatchingCount {
	@Test
	public void testMethods2() {
		assertBug(Methods.class, MachingCountCheck1.class, 7);
	}

	@Test
	public void testMethods3() {
		assertNoBug(Methods.class, MachingCountCheck2.class);
	}
}

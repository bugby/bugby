package org.bugby.pattern.example.test.annotations;

import static org.bugby.pattern.example.MatchingHelper.assertBug;
import static org.bugby.pattern.example.MatchingHelper.assertNoBug;

import org.junit.Test;

public class TestCorrelation {
	@Test
	public void testMatch1() {
		assertBug(CorrelationPattern.class, CorrelationMatch1.class, 6);
	}

	@Test
	public void testMatch2() {
		assertBug(CorrelationPattern.class, CorrelationMatch2.class, 7);
	}

	@Test
	public void testNotMatch() {
		assertNoBug(CorrelationPattern.class, CorrelationNotMatch.class);
	}
}

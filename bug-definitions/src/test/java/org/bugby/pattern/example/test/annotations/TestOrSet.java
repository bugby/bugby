package org.bugby.pattern.example.test.annotations;

import static org.bugby.pattern.example.MatchingHelper.assertBug;
import static org.bugby.pattern.example.MatchingHelper.assertNoBug;

import org.junit.Test;

public class TestOrSet {
	@Test
	public void testMatch() {
		assertBug(OrSetPattern.class, OrSetMatch.class, 4);
	}

	@Test
	public void testNotMatch() {
		assertNoBug(OrSetPattern.class, OrSetNotMatch.class);
	}
}

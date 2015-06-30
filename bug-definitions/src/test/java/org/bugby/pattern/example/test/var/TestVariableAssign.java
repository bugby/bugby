package org.bugby.pattern.example.test.var;

import static org.bugby.pattern.example.MatchingHelper.assertBug;
import static org.bugby.pattern.example.MatchingHelper.assertNoBug;

import org.junit.Test;

public class TestVariableAssign {
	@Test
	public void testAssign1() {
		assertBug(VariableAssign.class, VariableAssignCheck1.class, 15);
	}

	@Test
	public void testAssign2() {
		assertNoBug(VariableAssign.class, VariableAssignCheck2.class);
	}
}

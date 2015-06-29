package org.bugby.pattern.example.test;

import static org.bugby.pattern.example.MatchingHelper.assertBug;

import org.junit.Test;

public class TestVariableAssign {
	@Test
	public void testAssign1() {
		assertBug(VariableAssign.class, VariableAssignCheck1.class, 15);
	}

}

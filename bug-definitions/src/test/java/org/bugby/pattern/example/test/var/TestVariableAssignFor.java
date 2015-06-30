package org.bugby.pattern.example.test.var;

import static org.bugby.pattern.example.MatchingHelper.assertBug;

import org.junit.Test;

public class TestVariableAssignFor {
	@Test
	public void testAssign1() {
		assertBug(VariableAssignFor.class, VariableAssignForCheck1.class, 9);
	}

}

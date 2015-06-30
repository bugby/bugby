package org.bugby.pattern.example.test.var;

import static org.bugby.pattern.example.MatchingHelper.assertBug;

import org.junit.Test;

public class TestVariableAssignField {
	@Test
	public void testAssign1() {
		assertBug(VariableAssignField.class, VariableAssignFieldCheck1.class, 8);
	}

}

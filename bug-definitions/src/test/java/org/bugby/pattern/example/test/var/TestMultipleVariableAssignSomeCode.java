package org.bugby.pattern.example.test.var;

import static org.bugby.pattern.example.MatchingHelper.assertBug;

import org.junit.Test;

public class TestMultipleVariableAssignSomeCode {
	@Test
	public void testAssign1() {
		assertBug(MultipleVariableAssignSomeCode.class, MultipleVariableAssignSomeCodeCheck1.class, 8);
	}

}

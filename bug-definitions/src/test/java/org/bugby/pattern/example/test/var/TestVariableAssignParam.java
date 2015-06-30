package org.bugby.pattern.example.test.var;

import static org.bugby.pattern.example.MatchingHelper.assertBug;
import static org.bugby.pattern.example.MatchingHelper.assertNoBug;

import org.junit.Test;

public class TestVariableAssignParam {
	@Test
	public void testAssign1() {
		assertBug(VariableAssignParam.class, VariableAssignParamCheck1.class, 10);
	}

	@Test
	public void testAssign2() {
		assertNoBug(VariableAssignParam.class, VariableAssignParamCheck2.class);
	}

}

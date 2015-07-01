package org.bugby.pattern.example.test.var;

import static org.bugby.pattern.example.MatchingHelper.assertBug;
import static org.bugby.pattern.example.MatchingHelper.assertNoBug;

import org.junit.Test;

public class TestVariableAssignCustomType {
	@Test
	public void testAssign1() {
		assertNoBug(VariableAssignCustomType.class, VariableAssignCustomTypeCheck1.class);
	}

	@Test
	public void testAssign2() {
		assertBug(VariableAssignCustomType.class, VariableAssignCustomTypeCheck2.class, 7);
	}

}

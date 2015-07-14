package org.bugby.pattern.example.test.var;

import static org.bugby.pattern.example.MatchingHelper.assertBug;

import org.junit.Test;

public class TestVariableAssignDecl {
	@Test
	public void testAssign1() {
		assertBug(VariableAssignDecl.class, VariableAssignDeclCheck1.class, 8);
	}

}

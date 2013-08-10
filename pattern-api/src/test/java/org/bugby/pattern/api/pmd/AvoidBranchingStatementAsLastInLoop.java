package org.bugby.pattern.api.pmd;

import java.util.Collections;

import junit.framework.Assert;

import org.bugby.pattern.api.BugDefinition;
import org.bugby.pattern.api.CodePattern;
import org.bugby.pattern.api.matcher.content.Matchers;
import org.bugby.pattern.api.matcher.structure.ChildDefinition;
import org.bugby.pattern.api.model.Block;
import org.bugby.pattern.api.model.BreakStmt;
import org.bugby.pattern.api.model.Expr;
import org.bugby.pattern.api.model.ForStmt;
import org.junit.Test;

public class AvoidBranchingStatementAsLastInLoop {

	@Test
	public void test() {

		//TODO how to represent lists of Nodes? 
		/*Using a ContentMatcher is not good, because matching will be done on structure, and thus by the structural matching
		 * algorithm. And using a simple List<? extends Node> is not good wither, because it cannot model things like "any number of statements that look like this"
		 * Or the fact that we are only interested in the last statement in the list... Just as in this example
		 * */

		//TODO why is it that type inference is unable to infer there generic types?
		BugDefinition bugDefinition = new BugDefinition(//
				new CodePattern(//
						//any for loop
						new ForStmt(null, ChildDefinition.<Expr> anyChild(), null, ChildDefinition.directChild(//
								new Block(Collections.singletonList(new BreakStmt(Matchers.<String> any())))// TODO model that fact that this a list of statements that ends with a break (currently it is only a break)
								))),//
				Collections.<CodePattern> emptyList(),//
				Collections.<CodePattern> emptyList()//
		);

		//just testing if building the pattern is possible, and how
		Assert.assertNotNull(bugDefinition);
	}
}

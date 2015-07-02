package org.bugby.matcher.statement;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.DoWhileLoopTree;
import com.sun.source.tree.Tree;

public class DoWhileLoopMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher conditionMatcher;
	private final TreeMatcher statementMatcher;

	public DoWhileLoopMatcher(DoWhileLoopTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		this.conditionMatcher = factory.build(patternNode.getCondition());
		this.statementMatcher = factory.build(patternNode.getStatement());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof DoWhileLoopTree)) {
			return match.done(false);
		}
		DoWhileLoopTree ct = (DoWhileLoopTree) node;

		match.child(ct.getCondition(), conditionMatcher);
		match.child(ct.getStatement(), statementMatcher);

		return match.done();
	}

}

package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

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

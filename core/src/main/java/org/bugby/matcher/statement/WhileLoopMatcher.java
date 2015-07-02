package org.bugby.matcher.statement;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.Tree;
import com.sun.source.tree.WhileLoopTree;

public class WhileLoopMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher conditionMatcher;
	private final TreeMatcher statementMatcher;

	public WhileLoopMatcher(WhileLoopTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);

		this.conditionMatcher = factory.build(patternNode.getCondition());
		this.statementMatcher = factory.build(patternNode.getStatement());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof WhileLoopTree)) {
			return match.done(false);
		}
		WhileLoopTree ct = (WhileLoopTree) node;

		match.child(ct.getCondition(), conditionMatcher);
		match.child(ct.getStatement(), statementMatcher);

		return match.done();
	}

}

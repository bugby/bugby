package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;
import com.sun.source.tree.WhileLoopTree;

public class WhileLoopMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final WhileLoopTree patternNode;
	private final TreeMatcher conditionMatcher;
	private final TreeMatcher statementMatcher;

	public WhileLoopMatcher(WhileLoopTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
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

package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.LabeledStatementTree;
import com.sun.source.tree.Tree;

public class LabeledMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final LabeledStatementTree patternNode;
	private final TreeMatcher statementMatcher;

	public LabeledMatcher(LabeledStatementTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.statementMatcher = factory.build(patternNode.getStatement());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof LabeledStatementTree)) {
			return match.done(false);
		}
		LabeledStatementTree mt = (LabeledStatementTree) node;

		match.self(patternNode.getLabel().toString().equals(mt.getLabel().toString()));
		match.child(mt.getStatement(), statementMatcher);

		return match.done();
	}

}

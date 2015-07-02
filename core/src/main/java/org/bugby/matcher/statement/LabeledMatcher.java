package org.bugby.matcher.statement;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.LabeledStatementTree;
import com.sun.source.tree.Tree;

public class LabeledMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher statementMatcher;

	public LabeledMatcher(LabeledStatementTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);

		this.statementMatcher = factory.build(patternNode.getStatement());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof LabeledStatementTree)) {
			return match.done(false);
		}
		LabeledStatementTree mt = (LabeledStatementTree) node;

		match.self(((LabeledStatementTree) getPatternNode()).getLabel().toString().equals(mt.getLabel().toString()));
		match.child(mt.getStatement(), statementMatcher);

		return match.done();
	}

}

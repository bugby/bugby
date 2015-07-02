package org.bugby.matcher.statement;

import java.util.List;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.CaseTree;
import com.sun.source.tree.Tree;

public class CaseMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher expressionMatcher;
	private final List<TreeMatcher> statementsMatchers;

	public CaseMatcher(CaseTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		this.expressionMatcher = factory.build(patternNode.getExpression());
		this.statementsMatchers = build(factory, patternNode.getStatements());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof CaseTree)) {
			return match.done(false);
		}
		CaseTree ct = (CaseTree) node;

		match.child(ct.getExpression(), expressionMatcher);
		match.orderedChildren(ct.getStatements(), statementsMatchers);

		return match.done();

	}

}

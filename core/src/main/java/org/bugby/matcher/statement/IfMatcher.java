package org.bugby.matcher.statement;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.IfTree;
import com.sun.source.tree.Tree;

public class IfMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher conditionMatcher;
	private final TreeMatcher thenMatcher;
	private final TreeMatcher elseMatcher;

	public IfMatcher(IfTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);

		this.conditionMatcher = factory.build(patternNode.getCondition());
		this.thenMatcher = factory.build(patternNode.getThenStatement());
		this.elseMatcher = factory.build(patternNode.getElseStatement());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof IfTree)) {
			return match.done(false);
		}
		IfTree ct = (IfTree) node;

		match.child(ct.getCondition(), conditionMatcher);
		match.child(ct.getThenStatement(), thenMatcher);
		match.child(ct.getElseStatement(), elseMatcher);

		return match.done();
	}

}

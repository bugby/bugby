package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.Multimap;
import com.sun.source.tree.IfTree;
import com.sun.source.tree.Tree;

public class IfMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final IfTree patternNode;
	private final TreeMatcher conditionMatcher;
	private final TreeMatcher thenMatcher;
	private final TreeMatcher elseMatcher;

	public IfMatcher(IfTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
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

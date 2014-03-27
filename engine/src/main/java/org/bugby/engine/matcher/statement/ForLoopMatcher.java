package org.bugby.engine.matcher.statement;

import java.util.List;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.Multimap;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.Tree;

public class ForLoopMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final ForLoopTree patternNode;
	private final List<TreeMatcher> initializerMatchers;
	private final TreeMatcher conditionMatcher;
	private final List<TreeMatcher> updateMatchers;
	private final TreeMatcher statementMatcher;

	public ForLoopMatcher(ForLoopTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.initializerMatchers = build(factory, patternNode.getInitializer());
		this.conditionMatcher = factory.build(patternNode.getCondition());
		this.updateMatchers = build(factory, patternNode.getUpdate());
		this.statementMatcher = factory.build(patternNode.getStatement());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof ForLoopTree)) {
			return match.done(false);
		}
		ForLoopTree ct = (ForLoopTree) node;

		
		match.unorderedChildren(ct.getInitializer(), initializerMatchers);
		match.unorderedChildren(ct.getUpdate(), updateMatchers);
		match.child(ct.getCondition(), conditionMatcher);
		match.child(ct.getStatement(), statementMatcher);

		return match.done();
	}

}

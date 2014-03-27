package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.Multimap;
import com.sun.source.tree.AssertTree;
import com.sun.source.tree.Tree;

public class AssertMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final AssertTree patternNode;
	private final TreeMatcher conditionMatcher;
	private final TreeMatcher detailMatcher;

	public AssertMatcher(AssertTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.conditionMatcher = factory.build(patternNode.getCondition());
		this.detailMatcher = factory.build(patternNode.getDetail());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof AssertTree)) {
			return match.done(false);
		}
		AssertTree mt = (AssertTree) node;

		
		match.child(mt.getCondition(), conditionMatcher);
		match.child(mt.getDetail(), detailMatcher);

		return match.done();
	}

}

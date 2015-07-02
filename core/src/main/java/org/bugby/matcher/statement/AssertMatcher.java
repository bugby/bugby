package org.bugby.matcher.statement;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.AssertTree;
import com.sun.source.tree.Tree;

public class AssertMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher conditionMatcher;
	private final TreeMatcher detailMatcher;

	public AssertMatcher(AssertTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
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

package org.bugby.engine.matcher.statement;

import java.util.List;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.SwitchTree;
import com.sun.source.tree.Tree;

public class SwitchMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher expressionMatcher;
	private final List<TreeMatcher> casesMatchers;

	public SwitchMatcher(SwitchTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);

		this.expressionMatcher = factory.build(patternNode.getExpression());
		this.casesMatchers = build(factory, patternNode.getCases());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof SwitchTree)) {
			return match.done(false);
		}
		SwitchTree mt = (SwitchTree) node;

		match.child(mt.getExpression(), expressionMatcher);
		match.unorderedChildren(mt.getCases(), casesMatchers);

		return match.done();
	}

}

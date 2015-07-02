package org.bugby.matcher.statement;

import java.util.List;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

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

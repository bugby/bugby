package org.bugby.matcher.statement;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.EmptyStatementTree;
import com.sun.source.tree.Tree;

public class EmptyStatementMatcher extends DefaultTreeMatcher implements TreeMatcher {

	public EmptyStatementMatcher(EmptyStatementTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof EmptyStatementTree)) {
			return match.done(false);
		}
		return match.done(true);
	}

}

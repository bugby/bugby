package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

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

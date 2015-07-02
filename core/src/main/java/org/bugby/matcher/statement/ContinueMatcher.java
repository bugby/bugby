package org.bugby.matcher.statement;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.ContinueTree;
import com.sun.source.tree.Tree;

public class ContinueMatcher extends DefaultTreeMatcher implements TreeMatcher {

	public ContinueMatcher(ContinueTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof ContinueTree)) {
			return match.done(false);
		}
		return match.done(true);
	}

}

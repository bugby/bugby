package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.BreakTree;
import com.sun.source.tree.Tree;

public class BreakMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final BreakTree patternNode;

	public BreakMatcher(BreakTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
	}

	public BreakTree getPatternNode() {
		return patternNode;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof BreakTree)) {
			return match.done(false);
		}
		return match.done(true);
	}

}

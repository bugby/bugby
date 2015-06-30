package org.bugby.wildcard.matcher;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.BreakTree;
import com.sun.source.tree.ContinueTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.Tree;

public class AnyBranchMatcher extends DefaultTreeMatcher implements TreeMatcher {

	public AnyBranchMatcher(Tree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		// TODO should match intantiation blocks
		if (!(node instanceof BreakTree) && !(node instanceof ContinueTree) && !(node instanceof ReturnTree)) {
			return match.done(false);
		}

		match.self(true);

		return match.done();
	}

}

package org.bugby.matcher.wildcard;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

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

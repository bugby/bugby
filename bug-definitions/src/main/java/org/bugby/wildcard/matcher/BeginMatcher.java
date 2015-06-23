package org.bugby.wildcard.matcher;

import java.util.List;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.MatchingType;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.Tree;

public class BeginMatcher extends DefaultTreeMatcher implements TreeMatcher {

	public BeginMatcher(Tree patternNode, TreeMatcherFactory factory) {
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.begin;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		List<Tree> list = context.getSiblingsOf(node);
		if (list != null && list.size() > 0 && list.get(0) == node) {
			return match.self(true).done();
		}
		return match.done(false);
	}

}

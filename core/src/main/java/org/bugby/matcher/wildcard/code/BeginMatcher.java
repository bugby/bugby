package org.bugby.matcher.wildcard.code;

import java.util.List;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.MatchingType;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.Tree;

public class BeginMatcher extends DefaultTreeMatcher implements TreeMatcher {

	public BeginMatcher(Tree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
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

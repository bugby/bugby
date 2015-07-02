package org.bugby.matcher.wildcard.code;

import java.util.List;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.MatchingType;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.Tree;

public class EndMatcher extends DefaultTreeMatcher implements TreeMatcher {
	public EndMatcher(Tree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.end;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		List<Tree> list = context.getSiblingsOf(node);
		if (list != null && list.size() > 0 && list.get(list.size() - 1) == node) {
			return match.done(true);
		}
		return match.done(false);
	}

}

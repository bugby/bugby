package org.bugby.matcher.wildcard.code;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.MatchingType;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.Tree;

public class NoCodeMatcher extends DefaultTreeMatcher implements TreeMatcher {
	public NoCodeMatcher(Tree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.empty;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		// TODO Auto-generated method stub
		return false;
	}

}

package org.bugby.wildcard.matcher.var;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.Tree;

public class SomeFieldMatcher extends SomeVariableMatcher {

	public SomeFieldMatcher(Tree patternNode, TreeMatcherFactory factory) {
		super(patternNode, factory);
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		//TODO add also visibility modifiers if needed
		return super.matches(node, context);
	}
}

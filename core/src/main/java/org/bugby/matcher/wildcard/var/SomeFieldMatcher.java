package org.bugby.matcher.wildcard.var;

import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcherFactory;

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

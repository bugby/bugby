package org.bugby.wildcard.matcher.var;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class SomeParamMatcher extends SomeVariableMatcher {

	public SomeParamMatcher(VariableTree patternNode, TreeMatcherFactory factory) {
		super(patternNode, factory);
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		//TODO check that it's actually a parameter
		return super.matches(node, context);
	}

}

package org.bugby.matcher.wildcard.var;

import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class SomeVariableMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher delegateMatcher;

	public SomeVariableMatcher(Tree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		if (patternNode instanceof VariableTree) {
			delegateMatcher = new SomeVariableDefMatcher((VariableTree) patternNode, factory);
		} else if (patternNode instanceof IdentifierTree) {
			delegateMatcher = new SomeVariableUsageMatcher((IdentifierTree) patternNode, factory);
		} else {
			throw new RuntimeException("Wrong node type:" + patternNode);
		}
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		return delegateMatcher.matches(node, context);
	}

	@Override
	public String toString() {
		return delegateMatcher.toString();
	}
}

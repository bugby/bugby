package org.bugby.matcher.wildcard.type;

import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;

public class SomeTypeMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher delegateMatcher;

	public SomeTypeMatcher(Tree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		if (patternNode instanceof ClassTree) {
			delegateMatcher = null;//new DynamicTypeDefinitionMatcher((ClassTree) patternNode, factory);
		} else if (patternNode instanceof IdentifierTree) {
			delegateMatcher = new DynamicTypeInvocationMatcher((IdentifierTree) patternNode, factory);
		} else {
			throw new RuntimeException("Wrong node type:" + patternNode);
		}
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		return delegateMatcher.matches(node, context);
	}
}

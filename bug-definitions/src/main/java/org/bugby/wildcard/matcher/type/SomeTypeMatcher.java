package org.bugby.wildcard.matcher.type;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;

public class SomeTypeMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final Tree patternNode;
	private final TreeMatcher delegateMatcher;

	public SomeTypeMatcher(Tree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		if (patternNode instanceof ClassTree) {
			delegateMatcher = new DynamicTypeDefinitionMatcher((ClassTree) patternNode, factory);
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

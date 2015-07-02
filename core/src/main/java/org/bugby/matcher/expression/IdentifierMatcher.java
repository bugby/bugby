package org.bugby.matcher.expression;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;

public class IdentifierMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher delegateMatcher;

	public IdentifierMatcher(IdentifierTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		Element element = TreeUtils.elementFromUse(patternNode);
		if (element instanceof TypeElement) {
			delegateMatcher = new IdentifierTypeMatcher(patternNode, factory);
		} else {
			delegateMatcher = new IdentifierVariableMatcher(patternNode, factory);
		}
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		return delegateMatcher.matches(node, context);
	}
}

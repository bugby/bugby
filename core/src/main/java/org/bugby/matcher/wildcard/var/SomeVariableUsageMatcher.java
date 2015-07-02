package org.bugby.matcher.wildcard.var;

import javax.lang.model.element.Element;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.MatchingValueKey;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class SomeVariableUsageMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final Element patternVariableElement;

	public SomeVariableUsageMatcher(IdentifierTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		patternVariableElement = getVariableElement(patternNode);
	}

	private Element getVariableElement(Tree node) {
		if (node instanceof VariableTree) {
			return TreeUtils.elementFromDeclaration((VariableTree) node);
		}
		if (node instanceof IdentifierTree) {
			return TreeUtils.elementFromUse((IdentifierTree) node);
		}
		return null;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof IdentifierTree)) {
			return match.done(false);
		}
		Element sourceVarElement = getVariableElement(node);
		if (sourceVarElement == null) {
			return match.done(false);
		}

		MatchingValueKey matchingKey = new MatchingValueKey("VAR", patternVariableElement);
		Element currentMapping = context.getValue(matchingKey);
		match.self(sourceVarElement.equals(currentMapping));
		return match.done();
	}

	@Override
	public String toString() {
		return "SomeVariableMatcher on " + patternVariableElement;
	}
}

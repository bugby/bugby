package org.bugby.matcher.expression;

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

public class IdentifierVariableMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final Element patternVariableElement;

	public IdentifierVariableMatcher(IdentifierTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		patternVariableElement = getVariableElement(patternNode);
	}

	private Element getVariableElement(Tree node) {
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
		IdentifierTree mt = (IdentifierTree) node;

		if (((IdentifierTree) getPatternNode()).getName().toString().equals("this") && mt.getName().toString().equals("this")) {
			// this matches this - TODO what about super !?
			return match.done(true);
		}
		if (((IdentifierTree) getPatternNode()).getName().toString().equals("super") && mt.getName().toString().equals("super")) {
			// this matches this - TODO what about super !?
			return match.done(true);
		}
		Element sourceVarElement = getVariableElement(node);
		if (sourceVarElement == null) {
			return match.done(false);
		}

		// TODO - use annotation
		// match.self(mt.getName().toString().equals(((IdentifierTree) getPatternNode()).getName().toString()));

		MatchingValueKey matchingKey = new MatchingValueKey("VAR", patternVariableElement);
		Element currentMapping = context.getValue(matchingKey);
		match.self(sourceVarElement.equals(currentMapping));
		return match.done();
	}
}
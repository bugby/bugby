package org.bugby.wildcard.matcher.var;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import org.bugby.api.javac.TreeUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.MatchingValueKey;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class SomeVariableMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final Tree patternNode;
	private final String variableName;
	private final Element patternVariableElement;
	private final MatchingValueKey matchingKey;

	// private final Scope scopeInPattern;

	public SomeVariableMatcher(Tree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		variableName = getVariableName(patternNode);
		patternVariableElement = getVariableElement(patternNode);
		matchingKey = new MatchingValueKey(getId(), "SomeVariableMatcher", "ELEMENT");
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

	private String getVariableName(Tree node) {
		if (node instanceof VariableTree) {
			return ((VariableTree) node).getName().toString();
		}
		if (node instanceof IdentifierTree) {
			return ((IdentifierTree) node).getName().toString();
		}
		return null;
	}

	private TypeMirror getVariableType(Tree node) {
		if (node instanceof VariableTree) {
			return TreeUtils.elementFromDeclaration((VariableTree) node).asType();
		}
		if (node instanceof IdentifierTree) {
			return TreeUtils.elementFromUse((IdentifierTree) node).asType();
		}
		return null;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		Element sourceVarElement = getVariableElement(node);
		if (sourceVarElement == null) {
			return match.done(false);
		}

		match.self(true);

		//TODO what about initializer!!
		Element currentMapping = context.getValue(matchingKey);
		if (currentMapping == null) {
			// no assignment made yet
			//			if (!context.setVariableMapping(variableName, currentVarName, getVariableType(node))) {
			//				// wrong type - no match
			//				return match.done(false);
			//			}
			context.putValue(matchingKey, sourceVarElement);
			System.out.println("!!!!!!!!! ASSIGN " + patternVariableElement + " to " + sourceVarElement);
			match.self(true);
		} else {
			match.self(currentMapping.equals(sourceVarElement));
		}

		return match.done();
	}

	@Override
	public String toString() {
		return "SomeVariableMatcher on " + variableName;
	}
}

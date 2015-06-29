package org.bugby.wildcard.matcher.var;

import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import org.bugby.api.javac.TreeUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.MatchingValueKey;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class SomeVariableMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final Tree patternNode;
	private final String variableName;
	private final Element patternVariableElement;
	private final boolean isDefinition;
	private final TreeMatcher initMatcher;
	private final TreeMatcher typeMatcher;

	// private final Scope scopeInPattern;

	public SomeVariableMatcher(Tree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		variableName = getVariableName(patternNode);
		patternVariableElement = getVariableElement(patternNode);
		isDefinition = patternNode instanceof VariableTree;
		if (patternNode instanceof VariableTree) {
			ExpressionTree init = ((VariableTree) patternNode).getInitializer();
			if (init != null) {
				initMatcher = factory.build(init);
			} else {
				initMatcher = null;
			}
			typeMatcher = factory.build(((VariableTree) patternNode).getType());
		} else {
			initMatcher = null;
			typeMatcher = null;
		}
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

		boolean isSourceNodeDefinition = node instanceof VariableTree;
		if (isDefinition != isSourceNodeDefinition) {
			// match usage with usage and definition with definition
			return match.done(false);
		}
		match.self(true);

		MatchingValueKey matchingKey = new MatchingValueKey("SomeVariableMatcher", patternVariableElement);

		Element currentMapping = context.getValue(matchingKey);
		if (currentMapping == null) {
			boolean variableAlreadyAssign = checkAlreadyAssigned(sourceVarElement, context.getValues());
			if (variableAlreadyAssign) {
				return match.done(false);
			}

			if (!isDefinition) {
				match.self(context.compatibleTypes(patternVariableElement.asType(), sourceVarElement.asType()));
			}
		} else {
			match.self(currentMapping.equals(sourceVarElement));
		}

		if (isDefinition) {
			VariableTree mt = (VariableTree) node;
			match.child(mt.getType(), typeMatcher);
			if (initMatcher != null) {
				match.child(mt.getInitializer(), initMatcher);
			}
		}

		if (currentMapping == null && match.isCurrentMatch()) {
			context.putValue(matchingKey, sourceVarElement);
			System.out.println("!!!!!!!!! ASSIGN " + patternVariableElement + " to " + sourceVarElement);
		}
		return match.done();
	}

	private boolean checkAlreadyAssigned(Element sourceVarElement, Map<MatchingValueKey, Object> values) {
		for (Map.Entry<MatchingValueKey, Object> entry : values.entrySet()) {
			if (entry.getKey().getMatcherName().equals("SomeVariableMatcher")) {
				Element assigned = (Element) entry.getValue();
				if (sourceVarElement.equals(assigned)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "SomeVariableMatcher on " + variableName;
	}
}

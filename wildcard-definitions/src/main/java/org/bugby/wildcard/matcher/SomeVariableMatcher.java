package org.bugby.wildcard.matcher;

import javax.lang.model.type.TypeMirror;

import org.bugby.api.javac.TreeUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class SomeVariableMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final Tree patternNode;
	private final String variableName;

	// private final Scope scopeInPattern;

	public SomeVariableMatcher(Tree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		variableName = getVariableName(patternNode);
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
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		String currentVarName = getVariableName(node);
		if (currentVarName == null) {
			return HashMultimap.create();
		}
		Multimap<TreeMatcher, Tree> result = null;
		result = matchSelf(result, node, true, context);

		String currentMapping = context.getVariableMapping(variableName);
		if (currentMapping == null) {
			// no assignment made yet
			if (!context.setVariableMapping(variableName, currentVarName, getVariableType(node))) {
				// wrong type - no match
				return HashMultimap.create();
			}
			System.out.println("!!!!!!!!! ASSIGN " + variableName + " to " + currentVarName);
			result = matchSelf(result, node, true, context);
		} else {
			result = matchSelf(result, node, currentMapping.equals(currentVarName), context);
		}

		return result;
	}

	@Override
	public String toString() {
		return "SomeVariableMatcher on " + variableName;
	}
}

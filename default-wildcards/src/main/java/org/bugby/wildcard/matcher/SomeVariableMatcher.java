package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.matcher.acr.TreeModel;
import org.bugby.wildcard.api.MatchingContext;
import org.bugby.wildcard.api.WildcardNodeMatcher;
import org.richast.node.ASTNodeData;
import org.richast.scope.Scope;
import org.richast.variable.Variable;

public class SomeVariableMatcher implements WildcardNodeMatcher {
	private final String name;
	private final Scope scopeInPattern;
	private final boolean ordered;

	public SomeVariableMatcher(String name, Scope scopeInPattern, boolean ordered) {
		this.name = name;
		this.scopeInPattern = scopeInPattern;
		this.ordered = ordered;
	}

	@Override
	public boolean matches(TreeModel<Node, Node> treeModel, Node node, MatchingContext context) {
		Variable varInSource = ASTNodeData.resolvedVariable(node);
		if (varInSource == null) {
			// not a variable node
			return false;
		}
		Variable currentVar = context.getVariableMapping(name, scopeInPattern);
		if (currentVar == null) {
			// no assignment made yet
			if (!context.setVariableMapping(name, scopeInPattern, varInSource)) {
				return false;
			}
			System.out.println("!!!!!!!!! ASSIGN " + name + " to " + varInSource.getName());
			return true;
		}
		// Scope scopeInSource = ASTNodeData.resolvedVariableScope(node);
		return currentVar == varInSource;
	}

	@Override
	public boolean isOrdered() {
		return ordered;
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.normal;
	}

	@Override
	public String toString() {
		return "SomeVariableMatcher on " + name;
	}
}

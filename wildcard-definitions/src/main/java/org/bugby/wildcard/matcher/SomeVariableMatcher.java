package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.WildcardNodeMatcher;
import org.bugby.matcher.tree.MatchingType;
import org.bugby.matcher.tree.TreeModel;
import org.richast.node.ASTNodeData;
import org.richast.scope.Scope;
import org.richast.variable.Variable;

public class SomeVariableMatcher implements WildcardNodeMatcher {
	private final String name;
	private final Scope scopeInPattern;

	public SomeVariableMatcher(String name, Scope scopeInPattern) {
		this.name = name;
		this.scopeInPattern = scopeInPattern;
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
	public boolean isOrdered(String childType) {
		return true;
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
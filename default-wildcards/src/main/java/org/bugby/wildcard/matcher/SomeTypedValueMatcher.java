package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.expr.Expression;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.matcher.acr.TreeModel;
import org.bugby.wildcard.api.MatchingContext;
import org.bugby.wildcard.api.WildcardNodeMatcher;
import org.richast.node.ASTNodeData;
import org.richast.type.TypeWrapper;
import org.richast.type.TypeWrappers;

public class SomeTypedValueMatcher implements WildcardNodeMatcher {
	private Class<?> checkType;

	@Override
	public boolean matches(TreeModel<Node, Node> treeModel, Node node, MatchingContext context) {
		if (node instanceof Expression) {
			TypeWrapper type = ASTNodeData.resolvedType(node);
			return TypeWrappers.wrap(checkType).equals(type);
		}
		return false;
	}

	@Override
	public boolean isOrdered() {
		return true;
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.normal;
	}
}

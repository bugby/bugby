package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.expr.Expression;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.WildcardNodeMatcher;
import org.bugby.matcher.tree.MatchingType;
import org.bugby.matcher.tree.TreeModel;
import org.richast.node.ASTNodeData;
import org.richast.type.TypeWrapper;
import org.richast.type.TypeWrappers;

public class SomeConditionUsingMatcher implements WildcardNodeMatcher {

	@Override
	public boolean matches(TreeModel<Node, Node> treeModel, Node node, MatchingContext context) {
		// check if type = boolean
		if (node instanceof Expression) {
			TypeWrapper type = ASTNodeData.resolvedType(node);
			return TypeWrappers.wrap(boolean.class).equals(type);
		}
		return false;
	}

	@Override
	public boolean isOrdered(String childType) {
		return true;
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.normal;
	}
}

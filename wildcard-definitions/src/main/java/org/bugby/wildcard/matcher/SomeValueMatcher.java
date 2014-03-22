package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.expr.Expression;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.WildcardNodeMatcher;
import org.bugby.matcher.tree.MatchingType;
import org.bugby.matcher.tree.TreeModel;
import org.richast.node.ASTNodeData;
import org.richast.type.TypeWrapper;

public class SomeValueMatcher implements WildcardNodeMatcher {
	private final TypeWrapper nodeType;

	public SomeValueMatcher(TypeWrapper nodeType) {
		this.nodeType = nodeType;
	}

	@Override
	public boolean matches(TreeModel<Node, Node> treeModel, Node node, MatchingContext context) {
		if (!(node instanceof Expression)) {
			return false;
		}
		if (nodeType == null) {
			return true;
		}
		return nodeType.equals(ASTNodeData.resolvedType(node));
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
